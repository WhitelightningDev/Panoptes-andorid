package com.mabbureau.panoptes;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class IdProfileActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2; // New constant for camera request
    private static final int STORAGE_PERMISSION_CODE = 100;

    private TextInputEditText idInput; // Remove nameInput as it's not needed
    private MaterialButton uploadIDButton, uploadSelfieButton, uploadFingerprintButton, uploadPORButton, skipButton;
    private TextView idUploadPlaceholder, selfieUploadPlaceholder, fingerprintUploadPlaceholder, porUploadPlaceholder;

    private Uri selectedFileUri;  // Store the selected file's URI
    private boolean isIDUpload = false; // Determine if the file being uploaded is ID or POR
    private String userName; // To hold the user's name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_profile);

        // Initialize Views
        idInput = findViewById(R.id.IDInput);
        uploadIDButton = findViewById(R.id.IDUploadButton);
        uploadSelfieButton = findViewById(R.id.SelfieUploadButton);
        uploadFingerprintButton = findViewById(R.id.FingerprintUploadButton);
        uploadPORButton = findViewById(R.id.UploadPORButton);
        skipButton = findViewById(R.id.skipButton);
        idUploadPlaceholder = findViewById(R.id.idUploadPlaceholder);
        selfieUploadPlaceholder = findViewById(R.id.selfieUploadPlaceholder);
        fingerprintUploadPlaceholder = findViewById(R.id.fingerprintUploadPlaceholder);
        porUploadPlaceholder = findViewById(R.id.porUploadPlaceholder);

        // Retrieve the user's name from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        userName = sharedPreferences.getString("name", "User"); // Default to "User" if not found

        // Set OnClickListener for Upload ID Button
        uploadIDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isIDUpload = true;  // Set flag for ID upload
                requestFilePermission();
            }
        });

        // Set OnClickListener for Upload ID Button
        uploadIDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isIDUpload = true;  // Set flag for ID upload
                requestFilePermission();
            }
        });

        // Set OnClickListener for Upload Selfie Button
        uploadSelfieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isIDUpload = false;  // Set flag for Selfie upload
                openCamera();  // Open camera instead of file picker
            }
        });

        // Set OnClickListener for Upload Fingerprint Button
        uploadFingerprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic to upload fingerprint (to be implemented)
            }
        });

        // Set OnClickListener for Upload POR Button
        uploadPORButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isIDUpload = false;  // Set flag for POR upload
                requestFilePermission();
            }
        });

        // Set OnClickListener for Skip Button
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });
    }

    // Method to request permission for external storage based on Android version
    private void requestFilePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {  // Android 13+
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, STORAGE_PERMISSION_CODE);
            } else {
                pickFile();  // If permission already granted
            }
        } else {  // Android 6 to 12
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            } else {
                pickFile();  // If permission already granted
            }
        }
    }

    // Method to open camera
    private void openCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    // Handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickFile();  // Permission granted
            } else {
                showToast("Permission Denied!");
            }
        }
    }

    // Method to open file picker
    private void pickFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");  // Allow both PDF and images
        String[] mimeTypes = {"application/pdf", "image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    // Handle result of file picker or camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle camera result
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Uri photoUri = data.getData();

            if (photoUri != null) {
                // Save the image with a custom name
                saveImage(photoUri, userName + " Selfie.jpg");
                selfieUploadPlaceholder.setText(String.format("Selfie uploaded: %s Selfie.jpg", userName));
                selfieUploadPlaceholder.setTextColor(Color.GREEN);  // Change color to indicate success
            }
        }

        // Handle result of file picker
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedFileUri = data.getData();
            String fileType = getFileType(selectedFileUri);
            String fileName = getFileNameFromUri(selectedFileUri);

            if (fileType.equals("pdf") || fileType.equals("jpg") || fileType.equals("jpeg") || fileType.equals("png")) {
                showToast("File selected: " + fileName);

                // Check if it is ID upload or POR upload and update the respective placeholder
                if (isIDUpload) {
                    idUploadPlaceholder.setText(String.format("ID file uploaded: %s", fileName));
                    idUploadPlaceholder.setTextColor(Color.GREEN);  // Change color to indicate success
                } else {
                    porUploadPlaceholder.setText(String.format("POR file uploaded: %s", fileName));
                    porUploadPlaceholder.setTextColor(Color.GREEN);  // Change color to indicate success
                }
            } else {
                showToast("Invalid file type. Please select a PDF or image file.");
                selectedFileUri = null;  // Reset if invalid
            }
        }
    }

    // Save image with a custom name
    private void saveImage(Uri imageUri, String imageName) {
        // Assuming you're saving the image to the app's private directory
        File imagePath = new File(getFilesDir(), imageName);
        try (FileOutputStream out = new FileOutputStream(imagePath)) {
            // Write image data to file
            out.write(imageUri.toString().getBytes()); // This needs to be updated with actual image data handling
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Failed to save image.");
        }
    }

    // Get file type based on URI
    private String getFileType(Uri uri) {
        String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));
        return extension != null ? extension : "";
    }

    // Extract file name from Uri
    private String getFileNameFromUri(Uri uri) {
        String result = null;

        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);

                    if (nameIndex >= 0) {  // Check if the column exists
                        result = cursor.getString(nameIndex);
                    }
                }
            }
        }

        if (result == null) {  // Fallback to the last path segment
            result = uri.getLastPathSegment();
        }

        return result;
    }

    // Helper function to show Toast messages
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Navigate to MainActivity
    private void goToMainActivity() {
        Intent intent = new Intent(IdProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Optionally close IdProfileActivity
    }
}
