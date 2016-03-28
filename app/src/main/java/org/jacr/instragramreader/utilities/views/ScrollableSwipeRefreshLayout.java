package org.jacr.instragramreader.utilities.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * This custom implementation for SwipeRefreshLayout is intended to fix the problem about scrolling
 * the items up of a list. The problem is that this kind of scrolling has conflicts with Refresh
 * indicator where it always shows that indicator. This approach lets scroll the content up and
 * the refresh indicator only shows up if the content of the list has reached its first item.
 * <p/>
 * http://stackoverflow.com/questions/23053799/scrollup-not-working-in-stickylistviewheader-with-swiperefreshlayout?rq=1
 * <p/>
 * Created by Jesus on 3/17/2016.
 */
public class ScrollableSwipeRefreshLayout extends SwipeRefreshLayout {

    private RecyclerView recyclerView;

    //<editor-fold desc="Constructors">

    public ScrollableSwipeRefreshLayout(Context context) {
        super(context);
    }

    public ScrollableSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //</editor-fold>

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    //<editor-fold desc="Overrides">

    @Override
    public boolean canChildScrollUp() {
        if (recyclerView != null) {
            // In order to scroll the items up:
            //
            // 1 -> the wrapped List must have at least one item
            // 2 -> And then, the first visible item must not be the first item
            // 3 -> If the first visible item is the first item, (we've reached the first item) make sure that its
            // top must not cross over the padding top of the wrapped List
            //
            // If the wrapped List is empty or, the first item is located below the padding top of
            // the wrapped List, we can allow performing refreshing now
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int firstItemPosition = 0;
            if (layoutManager instanceof GridLayoutManager) {
                firstItemPosition = ((GridLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
            } else if (layoutManager instanceof LinearLayoutManager) {
                firstItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
            }
            return (recyclerView.getChildCount() > 0) && (firstItemPosition > 0 || (recyclerView.getChildAt(0).getTop() < 0));
        } else {
            return super.canChildScrollUp(); // Fall back to default implementation
        }
    }

    //</editor-fold>

}
