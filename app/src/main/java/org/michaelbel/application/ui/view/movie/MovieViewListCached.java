package org.michaelbel.application.ui.view.movie;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.ui.view.widget.MaskImageView;
import org.michaelbel.application.util.ScreenUtils;

@SuppressWarnings("all")
public class MovieViewListCached extends FrameLayout {

    private MaskImageView posterImage;
    private TextView titleText;
    private TextView yearText;
    private TextView addedDateText;

    private Paint paint;
    private boolean divider;
    private Rect rect = new Rect();

    public MovieViewListCached(Context context) {
        super(context);

        setForeground(Theme.selectableItemBackgroundDrawable());
        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        if (paint == null) {
            paint = new Paint();
            paint.setStrokeWidth(1);
            paint.setColor(ContextCompat.getColor(context, Theme.dividerColor()));
        }

        posterImage = new MaskImageView(context);
        posterImage.setShapeDrawable(MaskImageView.CIRCLE);
        posterImage.setScaleType(ImageView.ScaleType.FIT_XY);
        posterImage.setLayoutParams(LayoutHelper.makeFrame(56, 56, Gravity.START, 8, 8, 0, 8));
        addView(posterImage);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, 72, Gravity.CENTER_VERTICAL, 66, 0, 0, 0));
        addView(linearLayout);

        titleText = new TextView(context);
        titleText.setLines(1);
        titleText.setMaxLines(2);
        titleText.setEllipsize(TextUtils.TruncateAt.END);
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        titleText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        titleText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        titleText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        linearLayout.addView(titleText);

        yearText = new TextView(context);
        yearText.setLines(1);
        yearText.setMaxLines(1);
        yearText.setSingleLine();
        yearText.setEllipsize(TextUtils.TruncateAt.END);
        yearText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        yearText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        yearText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        linearLayout.addView(yearText);

        addedDateText = new TextView(context);
        addedDateText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        addedDateText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        addedDateText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        addedDateText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        linearLayout.addView(addedDateText);
    }

    public MovieViewListCached setPoster(@NonNull String posterPath) {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        String size = prefs.getString("image_quality_poster", "w342");

        Picasso.with(getContext())
                .load(Url.getImage(posterPath, size))
                .placeholder(R.drawable.movie_placeholder)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(posterImage);
        return this;
    }

    public MovieViewListCached setTitle(@NonNull String title) {
        if (title != null) {
            titleText.setText(title);
        }

        return this;
    }

    public MovieViewListCached setYear(@NonNull String date) {
        if (date != null) {
            if (date.length() >= 4) {
                yearText.setText(date.substring(0, 4));
            }
        }

        return this;
    }

    public MovieViewListCached setAddedDate(String date) {
        addedDateText.setText(date);
        return this;
    }

    public MovieViewListCached setDivider(boolean divider) {
        this.divider = divider;
        setWillNotDraw(!divider);
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        int height = getMeasuredHeight();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (divider) {
            canvas.drawLine(ScreenUtils.dp(72), getHeight() - 1, getWidth() - getPaddingRight(), getHeight() - 1, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getForeground() != null) {
            if (rect.contains((int) event.getX(), (int) event.getY())) {
                return true;
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                getForeground().setHotspot(event.getX(), event.getY());
            }
        }

        return super.onTouchEvent(event);
    }
}