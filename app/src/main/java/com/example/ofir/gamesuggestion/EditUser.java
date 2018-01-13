package com.example.ofir.gamesuggestion;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditUser extends AppCompatActivity
{
    Uri uri = null;
    User user = Statics.user;
    static final int PICK_IMAGE = 1;
    static final int TAKE_PICTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        //setups the font
        Statics.font((LinearLayout) findViewById(R.id.edit_user));

        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
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
                    uri = data.getData();
                    ((ImageButton) findViewById(R.id.picture)).setImageURI(uri);
                }
                break;

            case TAKE_PICTURE:
                if (data.getData() != null)
                {
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

    public void done(View view)
    {
        finish();
    }

    public void updatePicture(View view)
    {
        if (uri != null)
        {
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
        else
            ((TextView) findViewById(R.id.error)).setText(getString(R.string.sign_up_error_8));
    }

    public void updatePassword(View view)
    {
        String password = ((TextView) findViewById(R.id.password)).getText().toString();
        if (password.length() > 0)
        {
            user.password = password;
            DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");
            DatabaseReference hopperRef = database.child(user.username);
            Map<String, Object> hopperUpdates = new HashMap<>();
            hopperUpdates.put("password", password);
            hopperRef.updateChildren(hopperUpdates);
        }
        else
            ((TextView) findViewById(R.id.error)).setText(getString(R.string.sign_up_error_2));
    }

    public void updateFirstName(View view)
    {
        String first_name = ((TextView) findViewById(R.id.first_name)).getText().toString();
        if (first_name.length() > 0)
        {
            user.firstName = first_name;
            DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");
            DatabaseReference hopperRef = database.child(user.username);
            Map<String, Object> hopperUpdates = new HashMap<>();
            hopperUpdates.put("firstname", first_name);
            hopperRef.updateChildren(hopperUpdates);
        }
        else
            ((TextView) findViewById(R.id.error)).setText(getString(R.string.sign_up_error_3));
    }

    public void updateLastName(View view)
    {
        String last_name = ((TextView) findViewById(R.id.last_name)).getText().toString();
        if (last_name.length() > 0)
        {
            user.lastName = last_name;
            DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");
            DatabaseReference hopperRef = database.child(user.username);
            Map<String, Object> hopperUpdates = new HashMap<>();
            hopperUpdates.put("lastname", last_name);
            hopperRef.updateChildren(hopperUpdates);
        }
        else
            ((TextView) findViewById(R.id.error)).setText(getString(R.string.sign_up_error_4));
    }

    public void updateStreet(View view)
    {
        String street = ((TextView) findViewById(R.id.street)).getText().toString();
        if (street.length() > 0)
        {
            user.street = street;
            DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");
            DatabaseReference hopperRef = database.child(user.username);
            Map<String, Object> hopperUpdates = new HashMap<>();
            hopperUpdates.put("street", street);
            hopperRef.updateChildren(hopperUpdates);
        }
        else
            ((TextView) findViewById(R.id.error)).setText(getString(R.string.sign_up_error_5));
    }

    public void updateEmail(View view)
    {
        String email = ((TextView) findViewById(R.id.email)).getText().toString();
        if (email.length() > 0)
        {
            user.email = email;
            DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");
            DatabaseReference hopperRef = database.child(user.username);
            Map<String, Object> hopperUpdates = new HashMap<>();
            hopperUpdates.put("email", email);
            hopperRef.updateChildren(hopperUpdates);
        }
        else
            ((TextView) findViewById(R.id.error)).setText(getString(R.string.sign_up_error_6));
    }
}
