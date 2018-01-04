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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("all")
public class MovieViewListBig extends FrameLayout {

    private ImageView posterImage;
    private TextView titleText;
    private TextView ratingText;
    private TextView popularityText;
    private TextView voteCountText;
    private TextView releaseDateText;
    private TextView overviewText;

    private Paint paint;
    private boolean divider;
    private Rect rect = new Rect();

    public MovieViewListBig(Context context) {
        super(context);

        setForeground(Theme.selectableItemBackgroundDrawable());
        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        if (paint == null) {
            paint = new Paint();
            paint.setStrokeWidth(1);
            paint.setColor(ContextCompat.getColor(context, Theme.dividerColor()));
        }

        posterImage = new ImageView(context);
        posterImage.setScaleType(ImageView.ScaleType.FIT_XY);
        posterImage.setLayoutParams(LayoutHelper.makeFrame(100, 150, Gravity.START, 3, 3, 0, 3));
        addView(posterImage);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, 150 - 8 - 8, 100 + 12, 8, 12, 8));
        addView(linearLayout);

        titleText = new TextView(context);
        titleText.setLines(1);
        titleText.setMaxLines(1);
        titleText.setSingleLine();
        titleText.setEllipsize(TextUtils.TruncateAt.END);
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.5F);
        titleText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        titleText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        titleText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 0));
        linearLayout.addView(titleText);

        FrameLayout infoLayout = new FrameLayout(context);
        infoLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 4, 0, 0));
        linearLayout.addView(infoLayout);

        LinearLayout ratingLayout = new LinearLayout(context);
        ratingLayout.setOrientation(LinearLayout.HORIZONTAL);
        ratingLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 0, 0, 0, 0));
        infoLayout.addView(ratingLayout);

        LinearLayout averageLayout = new LinearLayout(context);
        averageLayout.setOrientation(LinearLayout.HORIZONTAL);
        averageLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        ratingLayout.addView(averageLayout);

        ImageView averageIcon = new ImageView(context);
        averageIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_star_circle, ContextCompat.getColor(context, Theme.iconActiveColor())));
        averageIcon.setLayoutParams(LayoutHelper.makeLinear(15, 15, Gravity.START | Gravity.CENTER_VERTICAL, 0, 0, 0, 0));
        averageLayout.addView(averageIcon);

        ratingText = new TextView(context);
        ratingText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.5F);
        ratingText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        ratingText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        ratingText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 2, 0, 0, 0));
        averageLayout.addView(ratingText);

        LinearLayout countLayout = new LinearLayout(context);
        countLayout.setOrientation(LinearLayout.HORIZONTAL);
        countLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 10, 0, 0, 0));
        ratingLayout.addView(countLayout);

        voteCountText = new TextView(context);
        voteCountText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.5F);
        voteCountText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        voteCountText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        voteCountText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.BOTTOM));
        countLayout.addView(voteCountText);

        ImageView voteCountIcon = new ImageView(context);
        voteCountIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_account_multiple, ContextCompat.getColor(context, Theme.iconActiveColor())));
        voteCountIcon.setLayoutParams(LayoutHelper.makeLinear(13, 13, Gravity.START | Gravity.BOTTOM, 2, 0, 0, 1));
        countLayout.addView(voteCountIcon);

        releaseDateText = new TextView(context);
        releaseDateText.setLines(1);
        releaseDateText.setMaxLines(1);
        releaseDateText.setSingleLine();
        releaseDateText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        releaseDateText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        releaseDateText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        releaseDateText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END,0, 0, 0, 0));
        infoLayout.addView(releaseDateText);

        overviewText = new TextView(context);
        overviewText.setLines(1);
        overviewText.setMaxLines(5);
        overviewText.setEllipsize(TextUtils.TruncateAt.END);
        overviewText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        overviewText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        overviewText.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        overviewText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 0, 4, 0, 0));
        linearLayout.addView(overviewText);
    }

    public MovieViewListBig setPoster(@NonNull String posterPath) {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        String size = prefs.getString("image_quality_poster", "w342");

        Picasso.with(getContext())
                .load(Url.getImage(posterPath, size))
                .placeholder(R.drawable.movie_placeholder)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(posterImage);
        return this;
    }

    public MovieViewListBig setTitle(@NonNull String title) {
        if (title != null) {
            titleText.setText(title);
        }

        return this;
    }

    public MovieViewListBig setRating(String voteAverage) {
        ratingText.setText(voteAverage);
        return this;
    }

    public MovieViewListBig setVoteCount(String count) {
        voteCountText.setText(count);
        return this;
    }

    public MovieViewListBig setReleaseDate(@NonNull String releaseDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newFormat = new SimpleDateFormat("d MMM yyyy", Locale.getDefault());

        Date date = null;
        try {
            date = format.parse(releaseDate);
        } catch (ParseException e) {
            // todo Error.
        }

        releaseDateText.setText(newFormat.format(date));
        return this;
    }

    public MovieViewListBig setOverview(String overview) {
        overviewText.setText(overview);
        return this;
    }

    public MovieViewListBig setDivider(boolean divider) {
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
            //canvas.drawLine(ScreenUtils.dp(100 + 10), getHeight() - 1, getWidth() - getPaddingRight(), getHeight() - 1, paint);
            canvas.drawLine(getPaddingLeft(), getHeight() - 1, getWidth() - getPaddingRight(), getHeight() - 1, paint);
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