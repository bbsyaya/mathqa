package dekauliya.fyp.mathqa;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    private PermissionRequestErrorListener errorListener;
    private MultiplePermissionsListener cameraReadWritePermissionListeners;

    @StringRes(R.string.camera_permission) String cameraPermissionMsg;
    @StringRes(R.string.settings_str) String settingsStr;
    @StringRes(R.string.camera_rw_permission) String cameraReadWritePermissionMsg;

    @ViewById(android.R.id.content) ViewGroup rootView;
    @ViewById(R.id.image_preview) ImageView mImagePreview;
    private Uri mCropImageUri;
    public static final String IMAGE_URI = "imageUri";;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraReadWritePermissionListeners = SnackbarOnAnyDeniedMultiplePermissionsListener.Builder
                .with(rootView, cameraReadWritePermissionMsg)
                .withOpenSettingsButton("Settings")
                .build();

        errorListener = new PermissionRequestErrorListener() {
            @Override public void onError(DexterError error) {
                Log.e("Dexter", "There was an error: " + error.toString());
            }
        };

        Dexter.withActivity(this).continueRequestingPendingPermissions(cameraReadWritePermissionListeners);
    }


    @Click(R.id.btn_click_image) public void onSelectImageClick(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(cameraReadWritePermissionListeners)
                .withErrorListener(errorListener)
                .check();

        CropImage.startPickImageActivity(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();
                Intent intent = new Intent(this, ImagePreviewActivity.class);
                intent.putExtra(IMAGE_URI, resultUri);
//                mImagePreview.setImageURI(resultUri);
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error= result.getError();

                /* Error dialog */
                Toast.makeText(this, "Unable to capture or read image from device. " +
                        "Check if your app permission is correct", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(mCropImageUri);
            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void startCropImageActivity(Uri mCropImageUri) {
        CropImage.activity(mCropImageUri).start(this);
    }
}
