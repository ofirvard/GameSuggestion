package com.example.ofir.gamesuggestion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUp extends AppCompatActivity
{
    static final int PICK_IMAGE = 1;
    static final int TAKE_PICTURE = 2;
    boolean hasPicture = false;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //setups the font
        Statics.font((LinearLayout) findViewById(R.id.sign_up));

        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
    }

    public void done(View v)
    {
        String username = ((TextView) findViewById(R.id.username)).getText().toString(),
                password = ((TextView) findViewById(R.id.password)).getText().toString(),
                firstname = ((TextView) findViewById(R.id.first_name)).getText().toString(),
                lastname = ((TextView) findViewById(R.id.last_name)).getText().toString(),
                street = ((TextView) findViewById(R.id.street)).getText().toString(),
                email = ((TextView) findViewById(R.id.email)).getText().toString();

        //checks
        String error = "";
        boolean check = true;

        if (username.length() == 0)
        {
            error += getString(R.string.sign_up_error_1) + "\n";
            check = false;
        }
        if (password.length() == 0)
        {
            error += getString(R.string.sign_up_error_2) + "\n";
            check = false;
        }
        if (firstname.length() == 0)
        {
            error += getString(R.string.sign_up_error_3) + "\n";
            check = false;
        }
        if (lastname.length() == 0)
        {
            error += getString(R.string.sign_up_error_4) + "\n";
            check = false;
        }
        if (street.length() == 0)
        {
            error += getString(R.string.sign_up_error_5) + "\n";
            check = false;
        }
        if (email.length() == 0)
        {
            error += getString(R.string.sign_up_error_6) + "\n";
            check = false;
        }
        if (!hasPicture)
        {
            error += getString(R.string.sign_up_error_8);
            check = false;
        }
        if (check)
        {
            tryToWrite(new User(username, password, firstname, lastname, street, email, true));
        }
        else
        {
            ((TextView) findViewById(R.id.error)).setText(error);
            ((TextView) findViewById(R.id.error)).setMovementMethod(new ScrollingMovementMethod());
        }
    }

    public void tryToWrite(final User user)
    {
        //user info
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");
        ValueEventListener check = new ValueEventListener()
        {
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(user.username).exists())
                {
                    ((TextView) findViewById(R.id.error)).setText(getString(R.string.sign_up_error_7));
                    ((TextView) findViewById(R.id.error)).setMovementMethod(new ScrollingMovementMethod());
                }
                else
                    write(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.e("1", "onCancelled", databaseError.toException());
            }
        };
        database.addListenerForSingleValueEvent(check);

        //now picture
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference riversRef = storageRef.child("images/" + user.username + ".jpg");

        riversRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        // Get a URL to the uploaded content
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception exception)
                    {
                        // Handle unsuccessful uploads
                    }
                });
    }

    public void write(User user)
    {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");
        database.child(user.username).child("username").setValue(user.username);
        database.child(user.username).child("password").setValue(user.password);
        database.child(user.username).child("firstname").setValue(user.firstName);
        database.child(user.username).child("lastname").setValue(user.lastName);
        database.child(user.username).child("street").setValue(user.street);
        database.child(user.username).child("email").setValue(user.email);

        Toast toast = Toast.makeText(getApplicationContext(), R.string.sign_up_successful, Toast.LENGTH_SHORT);
        toast.show();

        finish();
    }

    public void pickPicture(View v)
    {
        PopupMenu popup = new PopupMenu(this, v);
        popup.inflate(R.menu.picture_menu);
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.take:
                        takePicture();
                        break;

                    case R.id.choose:
                        choosePicture();
                        break;
                }
                return true;
            }
        });
    }

    private void takePicture()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            // Create the File where the photo should go
            File photoFile = null;
            try
            {
                photoFile = createImageFile();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null)
            {
                uri = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(takePictureIntent, TAKE_PICTURE);
            }
        }
    }

    private void choosePicture()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case PICK_IMAGE:
                if (data != null)
                {
                    hasPicture = true;
                    uri = data.getData();
                    ((ImageButton) findViewById(R.id.picture)).setImageURI(uri);
                }
                break;

            case TAKE_PICTURE:
                if (data.getData() != null)
                {
                    hasPicture = true;
                    uri = data.getData();
                    ((ImageView) findViewById(R.id.picture)).setImageURI(uri);
                }
                break;
        }
    }

    private File createImageFile() throws IOException
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }
}
