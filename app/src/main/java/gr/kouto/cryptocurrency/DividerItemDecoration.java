package gr.kouto.cryptocurrency;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private final int numberSpaceDp;
    private final String orientation;

    public DividerItemDecoration(int numberSpaceDp, String orientation) {
        this.numberSpaceDp = numberSpaceDp;
        this.orientation = orientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (orientation.equalsIgnoreCase("VERTICAL")) {
            if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                outRect.bottom = numberSpaceDp;
            }
        }
        else if (orientation.equalsIgnoreCase("HORIZONTAL")) {
            if (parent.getAdapter().getItemCount() == 0)
                outRect.left = numberSpaceDp;
            if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                outRect.right = numberSpaceDp;
            }
        }
    }
}
