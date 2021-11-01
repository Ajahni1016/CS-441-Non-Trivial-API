package com.example.nontrivialapi;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private ArFragment arFragment;
    private Renderable renderable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.cam_view);
        setUpModel();
        setUpPlane();

    }

    private void setUpModel() {
        WeakReference<MainActivity> weakActivity = new WeakReference<>(this);

        ModelRenderable.builder()
                .setSource(
                        this,
                        Uri.parse(
                                "https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb"))
                .setIsFilamentGltf(true)
                .build()
                .thenAccept(
                        modelRenderable -> {
                            MainActivity activity = weakActivity.get();
                            if (activity != null) {
                                activity.renderable = modelRenderable;
                            }
                        });

    }

    private void setUpPlane(){
        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {
            Anchor anchor = hitResult.createAnchor();
            AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());
            createModel(anchorNode);
        }));
    }

    private void createModel(AnchorNode anchorNode){
        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
        node.setParent(anchorNode);
        node.setRenderable(renderable);
        node.select();
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