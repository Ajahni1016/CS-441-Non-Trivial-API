package com.example.nontrivialapi;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ArFragment arFragment;
    private GLSurfaceView cam_view;
    private ModelRenderable modelRenderable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cam_view = findViewById(R.id.cam_view);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.cam_view);
        setUpModel();


    }

    private void setUpModel() {
        ModelRenderable.builder().setSource(this, RenderableSource.builder().setSource(this, Uri.parse("BaxterModel.obj"), RenderableSource.SourceType.GLB)
                .setScale(1.0f).setRecenterMode(RenderableSource.RecenterMode.ROOT).build())
                .setRegistryId("BaxterModel.obj").build().thenAccept(renderable->modelRenderable=renderable);

    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (mSession == null) {
//            Exception exception = null;
//            String message = null;
//            try {
//                switch (ArCoreApk.getInstance().requestInstall(this, !installRequested)) {
//                    case INSTALL_REQUESTED:
//                        installRequested = true;
//                        return;
//                    case INSTALLED:
//                        break;
//                }
//
////                if (!CameraPermissionHelper.hasCameraPermission(this)) {
////                    System.out.println("Camera not being used");
////                    CameraPermissionHelper.requestCameraPermission(this);
////                    return;
////                }
//
//                // Create the session.
//                mSession = new Session(/* context= */ this);
//            } catch (UnavailableArcoreNotInstalledException
//                    | UnavailableUserDeclinedInstallationException e) {
//                message = "Please install ARCore";
//                exception = e;
//            } catch (UnavailableApkTooOldException e) {
//                message = "Please update ARCore";
//                exception = e;
//            } catch (UnavailableSdkTooOldException e) {
//                message = "Please update this app";
//                exception = e;
//            } catch (UnavailableDeviceNotCompatibleException e) {
//                message = "This device does not support AR";
//                exception = e;
//            } catch (Exception e) {
//                message = "Failed to create AR session";
//                exception = e;
//            }

//            if (message != null) {
//                messageSnackbarHelper.showError(this, message);
//                Log.e(TAG, "Exception creating session", exception);
//                return;
//            }
//        }

//    }


}


//metehan
//switched to mac/xcode/swift this week to test it out
//Still learning the ropes of xcode, so hasn't made much progress towards his app. Has been working on relearning the basics and hasn't started working on the api portion of the app yet