

package com.scommix.customviews;

import com.svimedu.scommix.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuickReturnListView extends ListView  {

	 int mItemCount;
	 int mItemOffsetY[];
	 boolean scrollIsComputed = false;
	 int mHeight;
	protected int mCurrentScrollState;
		protected int mRefreshState;
		static int pos;
	


	public QuickReturnListView(Context context) {
		super(context);
	
		
	}

	public QuickReturnListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	
	}
	
	public QuickReturnListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	
	}
	
	
	


	public int getListHeight() {
		return mHeight;
	}


	public void computeScrollY() {
		mHeight = 0;
		if(getAdapter()==null)
		{
			mItemCount=0;
		}
		else if(getAdapter().getCount()>0){
			mItemCount = getAdapter().getCount();
			if (mItemOffsetY == null) {
				mItemOffsetY = new int[mItemCount];
			}
			for (int i = 0; i < mItemCount; ++i) {
	
				View view = getAdapter().getView(i, null, this);
				view.measure(
						MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
						MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
				mHeight += view.getMeasuredHeight();
				mItemOffsetY[i] = mHeight;
				
				
			}
			scrollIsComputed = true;
		}
	
	
	}

	public boolean scrollYIsComputed() {
		return scrollIsComputed;
	}

	public int getComputedScrollY() {
	int nScrollY = 0, nItemY;
		View view = null;
		pos = getFirstVisiblePosition();
		if(getAdapter()==null)
		{
		
		}
		else if(getAdapter().getCount()>0)
		{
			view = getChildAt(0);
			nItemY = view.getTop();
			nScrollY = mItemOffsetY[pos] - nItemY;
		}
		return nScrollY;
	}




	
	
}