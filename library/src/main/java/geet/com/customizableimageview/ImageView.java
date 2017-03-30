package geet.com.customizableimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class ImageView extends AppCompatImageView {

    private static final int EDGES_ROUNDED = 0;
    private static final int BORDER_EDGES = 1;
    private static final float DEFAULT_CORNER_RADIUS = 0f;
    private static final float DEFAULT_CORNER_RADIUS_TOP_LEFT = 0f;
    private static final float DEFAULT_CORNER_RADIUS_TOP_RIGHT = 0f;
    private static final float DEFAULT_CORNER_RADIUS_BOTTOM_LEFT = 0f;
    private static final float DEFAULT_CORNER_RADIUS_BOTTOM_RIGHT = 0f;
    private static int defaultEdgesEnum = -1;
    private static int edgesEnum;

    private static float CORNER_RADIUS_TOP_LEFT = DEFAULT_CORNER_RADIUS;
    private static float CORNER_RADIUS_TOP_RIGHT = DEFAULT_CORNER_RADIUS;
    private static float CORNER_RADIUS_BOTTOM_LEFT = DEFAULT_CORNER_RADIUS;
    private static float CORNER_RADIUS_BOTTOM_RIGHT = DEFAULT_CORNER_RADIUS;


    public ImageView(Context context) {
        this(context, null, 0);

    }

    public ImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);
    }

    public static int getEdgesEnum() {
        return edgesEnum;
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (null == attrs)
            return;

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ImageView);
        edgesEnum = typedArray.getInteger(R.styleable.ImageView_edges, defaultEdgesEnum);
        CORNER_RADIUS_TOP_LEFT = typedArray.getDimension(R.styleable.ImageView_rounded_edges_radius_top_left, DEFAULT_CORNER_RADIUS);
        CORNER_RADIUS_TOP_RIGHT = typedArray.getDimension(R.styleable.ImageView_rounded_edges_radius_top_right, DEFAULT_CORNER_RADIUS);
        CORNER_RADIUS_BOTTOM_LEFT = typedArray.getDimension(R.styleable.ImageView_rounded_edges_radius_bottom_left, DEFAULT_CORNER_RADIUS);
        CORNER_RADIUS_BOTTOM_RIGHT = typedArray.getDimension(R.styleable.ImageView_rounded_edges_radius_bottom_right, DEFAULT_CORNER_RADIUS);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());

        if (getEdgesEnum() == BORDER_EDGES) {
            clipPath.addRoundRect(rect, new float[]{CORNER_RADIUS_TOP_LEFT, CORNER_RADIUS_TOP_LEFT,
                    CORNER_RADIUS_TOP_RIGHT, CORNER_RADIUS_TOP_RIGHT,
                    CORNER_RADIUS_BOTTOM_RIGHT, CORNER_RADIUS_BOTTOM_RIGHT,
                    CORNER_RADIUS_BOTTOM_LEFT, CORNER_RADIUS_BOTTOM_LEFT}, Path.Direction.CW);
        } else if (getEdgesEnum() == EDGES_ROUNDED) {
            clipPath.addRoundRect(rect, getWidth() * 2, getHeight() * 2, Path.Direction.CW);
            this.setScaleType(ScaleType.CENTER_INSIDE);
        } else {
            clipPath.addRoundRect(rect, 0, 0, Path.Direction.CW);
            setScaleType(ScaleType.FIT_XY);
        }

        canvas.clipPath(clipPath);

        super.onDraw(canvas);
    }
}
