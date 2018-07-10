package org.webmenu.webmenuapp.Animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * An animation that rotates the view on the Y axis between two specified angles.
 * This animation also adds a translation on the Z axis (depth) to improve the effect.
 */
public class Rotate3dAnimation extends Animation {
    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private boolean mReverse;
    private boolean mRepeat;
    private boolean mDegrees;
    private Camera mCamera;
 
    /**
     * Creates a new 3D rotation on the Y axis. The rotation is defined by its
     * start angle and its end angle. Both angles are in degrees. The rotation
     * is performed around a center point on the 2D space, definied by a pair
     * of X and Y coordinates, called centerX and centerY. When the animation
     * starts, a translation on the Z axis (depth) is performed. The length
     * of the translation can be specified, as well as whether the translation
     * should be reversed in time.
     *
     * @param fromDegrees the start angle of the 3D rotation
     * @param toDegrees the end angle of the 3D rotation
     * @param centerX the X center of the 3D rotation
     * @param centerY the Y center of the 3D rotation
     * @param reverse true if the translation should be reversed, false otherwise
     */
    public Rotate3dAnimation(float fromDegrees, float toDegrees,
            float centerX, float centerY, float depthZ, boolean reverse) {
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
        mReverse = reverse;
    }

	@Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }
 
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = mFromDegrees;
        // float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);
 
        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;

        camera.save();
 
        final Matrix matrix = t.getMatrix();
        camera.translate(0.0f, 0.0f, mCenterY);
        /*	if (mReverse) {
        	matrix.preTranslate(centerX, centerY);
            matrix.postTranslate(-centerX, -centerY);
         //   camera.translate(0.3f, 1.0f, mDepthZ * interpolatedTime);
        } else {
            //  matrix.preTranslate(-centerX, -centerY);
     //  matrix.postTranslate(centerX, centerY);
         //   camera.translate(1.0f, 0.3f, mDepthZ * (1.0f - interpolatedTime));
        }	*/
/*        camera.rotateX(degrees);
        camera.getMatrix(matrix);
        camera.restore();*/
        if (mRepeat) {
            float degrees = -10 + ((mToDegrees - -10) * interpolatedTime);
        	matrix.preTranslate(centerX, centerY);
            matrix.postTranslate(-centerX, -centerY);
        	matrix.postScale(1.0f, 1.0f, 1.0f ,0.3f);
        	if (mDegrees) {
        		camera.rotateX(degrees);
                camera.getMatrix(matrix);
                mDegrees = false;
        	} else {
        	  camera.rotateX(degrees);
              camera.getMatrix(matrix);
              mDegrees = true;
        	}
        	mRepeat = false;
        	camera.restore();
        //	matrix.preTranslate(centerX, centerY);
        //	matrix.postTranslate(0.0f, 0.0f);
        //	matrix.preScale(1.0f, 0.3f, 1.0f, 1.0f);
         //   camera.translate(0.3f, 1.0f, mDepthZ * interpolatedTime);
        } else {
            float degrees = -10 + ((mToDegrees - -10) * interpolatedTime);
        	mRepeat = true;
          matrix.preTranslate(-centerX, -centerY);
          matrix.postTranslate(centerX, centerY);
        //	camera.rotateX(degrees * 2);
        //	camera.getMatrix(matrix);
          //  matrix.preTranslate(-centerX, -centerY);
          //  matrix.postTranslate(0.0f, 1.0f);
          //  matrix.preScale(1.0f, 0.3f, 1.0f, 1.0f);
          	matrix.postScale(1.0f, 0.3f, 1.0f , 1.0f);
         //   camera.translate(1.0f, 0.3f, mDepthZ * (1.0f - interpolatedTime));
        	if (mDegrees) {
        		camera.rotateX(degrees);
                camera.getMatrix(matrix);
        	} else {
        	  camera.rotateX(degrees);
              camera.getMatrix(matrix);
              
        	}
        	camera.restore();
//      	  camera.rotateX(-degrees);
//          camera.getMatrix(matrix);
        //	matrix.preScale(1, 1, 1, 1);
          //  matrix.postScale(1, (float)0.3, 1, (float)0.3);
        }
    }
    
}
