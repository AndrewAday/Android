package course.examples.Graphics.CanvasBubbleSurfaceView;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

public class BubbleActivity extends Activity {

	BubbleView mBubbleView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.frame);
		final BubbleView bubbleView = new BubbleView(getApplicationContext(),
				BitmapFactory.decodeResource(getResources(), R.drawable.b128),
				BitmapFactory.decodeResource(getResources(), R.drawable.death));
	

		relativeLayout.addView(bubbleView);
	}

	private class BubbleView extends SurfaceView implements
			SurfaceHolder.Callback {

		private final Bitmap mBitmap, mbitmap2;

		private final DisplayMetrics mDisplay;
		private final int dispW, dispH, basecase;

        private boolean initiated = false;

        private boolean x1d, y1d, x2d, y2d;
		private float x1, y1, x2, y2;
        private Float[] coordinate_array;
        private Boolean[] direction_array;
		private final SurfaceHolder mSurfaceHolder;
		private final Paint mPainter = new Paint();
		private Thread mDrawingThread;

		private static final int MOVE_STEP = 1;

		public BubbleView(Context context, Bitmap bitmap, Bitmap bitmap2) {
			super(context);
			
			this.mBitmap = bitmap;
			this.mbitmap2=bitmap2;

            x1d = true;
            y1d = true;
            x2d = true;
            y2d = true;

            coordinate_array = new Float[] {x1, y1, x2, y2};
            direction_array = new Boolean[] {x1d, y1d, x2d, y2d};

			mDisplay = new DisplayMetrics();
			BubbleActivity.this.getWindowManager().getDefaultDisplay()
					.getMetrics(mDisplay);
			dispW = mDisplay.widthPixels - 200;
			dispH = mDisplay.heightPixels - 200;
            basecase = 200;


			getRand();
			
			
			mPainter.setAntiAlias(true);

			mSurfaceHolder = getHolder();
			mSurfaceHolder.addCallback(this);
		}
		private void getRand(){
			Random r = new Random();
			coordinate_array[0] = (float) r.nextInt(dispW);
			coordinate_array[1] = (float) r.nextInt(dispH);
			coordinate_array[2] = (float) r.nextInt(dispW);
			coordinate_array[3] = (float) r.nextInt(dispH);
		}

		private void drawBubble(Canvas canvas) {

			canvas.drawColor(Color.DKGRAY);
			canvas.drawBitmap(mBitmap, coordinate_array[0], coordinate_array[1], mPainter);
			canvas.drawBitmap(mbitmap2, coordinate_array[2], coordinate_array[3], mPainter);
		}

        private void moveStep() {
            for (int i = 0; i < coordinate_array.length; i++) {
                if (direction_array[i]) {
                    coordinate_array[i] += MOVE_STEP;
                } else {
                    coordinate_array[i] -= MOVE_STEP;
                }
            }
        }

		private boolean move() {
            if (!initiated) {
                getRand();
                moveStep();
                initiated = true;
                return true;
            }

			if ((coordinate_array[0] >= dispW
					|| coordinate_array[2] >= dispW
					|| coordinate_array[1] >= dispH
					|| coordinate_array[3] >= dispH) ||
                    coordinate_array[0] <= basecase || coordinate_array[2] <= basecase || coordinate_array[1] <= basecase || coordinate_array[3] <= basecase) {
                if (coordinate_array[0] >= dispW || coordinate_array[0] <= basecase)
                    direction_array[0] = !direction_array[0];
                if (coordinate_array[1] >= dispH || coordinate_array[1] <= basecase)
                    direction_array[1] = !direction_array[1];
                if (coordinate_array[2] >= dispW || coordinate_array[2] <= basecase)
                    direction_array[2] = !direction_array[2];
                if (coordinate_array[3] >= dispH || coordinate_array[3] <= basecase)
                    direction_array[3] = !direction_array[3];
				moveStep();
                Log.e("hit", "wall");
                Log.e("DISPW", dispW + "");
                Log.e("DISPH", dispH + "");
                Log.d("x1", coordinate_array[0] + "");
                Log.d("y1", coordinate_array[1] + "");
                Log.d("x2", coordinate_array[2] + "");
                Log.d("y2", coordinate_array[3] + "");

                return true;
			} else {
				moveStep();
                return true;
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			mDrawingThread = new Thread(new Runnable() {
				public void run() {
					Canvas canvas = null;
					while (!Thread.currentThread().isInterrupted() && move()) {
						canvas = mSurfaceHolder.lockCanvas();
						if (null != canvas) {
							drawBubble(canvas);
							mSurfaceHolder.unlockCanvasAndPost(canvas);
						}
					}
				}
			});
			mDrawingThread.start();
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			if (null != mDrawingThread)
				mDrawingThread.interrupt();
		}

	}
}