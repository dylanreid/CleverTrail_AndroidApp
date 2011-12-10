package com.clevertrail.mobile.viewtrail;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.clevertrail.mobile.R;
import com.clevertrail.mobile.R.id;
import com.clevertrail.mobile.R.layout;
import com.clevertrail.mobile.imageutils.ImageLoader;

public class Activity_ViewTrail_Details extends Activity {
	
	

	// when this activity starts, fill in the text from the clever trail article
	// being viewed
	public void onStart() {
		super.onStart();

		TextView tvOverview = (TextView) findViewById(R.id.txtOverview);
		tvOverview.setText(Object_TrailArticle.sOverview);
		TextView tvDirections = (TextView) findViewById(R.id.txtDirections);
		tvDirections.setText(Object_TrailArticle.sDirections);
		TextView tvDescription = (TextView) findViewById(R.id.txtDescription);
		tvDescription.setText(Object_TrailArticle.sDescription);
		TextView tvConditions = (TextView) findViewById(R.id.txtConditions);
		tvConditions.setText(Object_TrailArticle.sConditions);
		TextView tvFees = (TextView) findViewById(R.id.txtFees);
		tvFees.setText(Object_TrailArticle.sFees);
		TextView tvAmenities = (TextView) findViewById(R.id.txtAmenities);
		tvAmenities.setText(Object_TrailArticle.sAmenities);
		TextView tvMisc = (TextView) findViewById(R.id.txtMisc);
		tvMisc.setText(Object_TrailArticle.sMisc);

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.viewtrail_details);

		Button btnOverview = (Button) findViewById(R.id.btnOverview);
		btnOverview.setOnClickListener(onclickOverviewButton);
		Button btnDirections = (Button) findViewById(R.id.btnDirections);
		btnDirections.setOnClickListener(onclickDirectionsButton);
		Button btnDescription = (Button) findViewById(R.id.btnDescription);
		btnDescription.setOnClickListener(onclickDescriptionButton);
		Button btnConditions = (Button) findViewById(R.id.btnConditions);
		btnConditions.setOnClickListener(onclickConditionsButton);
		Button btnFees = (Button) findViewById(R.id.btnFees);
		btnFees.setOnClickListener(onclickFeesButton);
		Button btnAmenities = (Button) findViewById(R.id.btnAmenities);
		btnAmenities.setOnClickListener(onclickAmenitiesButton);
		Button btnMisc = (Button) findViewById(R.id.btnMisc);
		btnMisc.setOnClickListener(onclickMiscButton);

		Button btnExpand1 = (Button) findViewById(R.id.btnExpandCollapse1);
		btnExpand1.setOnClickListener(onclickExpandButton);
		Button btnExpand2 = (Button) findViewById(R.id.btnExpandCollapse2);
		btnExpand2.setOnClickListener(onclickExpandButton);
		
	}

	private OnClickListener onclickOverviewButton = new OnClickListener() {
		public void onClick(View v) {
			toggleSectionButtonState((TextView) findViewById(R.id.txtOverview),
					(Button) findViewById(R.id.btnOverview));
		}
	};
	

	private OnClickListener onclickDirectionsButton = new OnClickListener() {
		public void onClick(View v) {
			toggleSectionButtonState(
					(TextView) findViewById(R.id.txtDirections),
					(Button) findViewById(R.id.btnDirections));
		}
	}; 

	private OnClickListener onclickDescriptionButton = new OnClickListener() {
		public void onClick(View v) {
			toggleSectionButtonState(
					(TextView) findViewById(R.id.txtDescription),
					(Button) findViewById(R.id.btnDescription));
		}
	};

	private OnClickListener onclickConditionsButton = new OnClickListener() {
		public void onClick(View v) {
			toggleSectionButtonState(
					(TextView) findViewById(R.id.txtConditions),
					(Button) findViewById(R.id.btnConditions));
		}
	};

	private OnClickListener onclickFeesButton = new OnClickListener() {
		public void onClick(View v) {
			toggleSectionButtonState((TextView) findViewById(R.id.txtFees),
					(Button) findViewById(R.id.btnFees));
		}
	};

	private OnClickListener onclickAmenitiesButton = new OnClickListener() {
		public void onClick(View v) {
			toggleSectionButtonState(
					(TextView) findViewById(R.id.txtAmenities),
					(Button) findViewById(R.id.btnAmenities));
		}
	};

	private OnClickListener onclickMiscButton = new OnClickListener() {
		public void onClick(View v) {
			toggleSectionButtonState((TextView) findViewById(R.id.txtMisc),
					(Button) findViewById(R.id.btnMisc));
		}
	};

	// toggle the state of a section button and textview
	private void toggleSectionButtonState(TextView tvSection, Button btnSection) {
		if (tvSection != null && btnSection != null) {
			if (tvSection.getVisibility() == View.VISIBLE) {
				tvSection.setVisibility(View.GONE);
				btnSection.setText("Show");
			} else {
				tvSection.setVisibility(View.VISIBLE);
				btnSection.setText("Hide");
			}
		}
	}

	// directly set the state of the button and textview
	private void setSectionButtonState(TextView tvSection, Button btnSection,
			int nViewState) {
		if (tvSection != null && btnSection != null) {
			if (nViewState == View.GONE) {
				tvSection.setVisibility(View.GONE);
				btnSection.setText("Show");
			} else {
				tvSection.setVisibility(View.VISIBLE);
				btnSection.setText("Hide");
			}
		}
	}

	private OnClickListener onclickExpandButton = new OnClickListener() {
		public void onClick(View v) {
			Button btnExpand1 = (Button) findViewById(R.id.btnExpandCollapse1);
			Button btnExpand2 = (Button) findViewById(R.id.btnExpandCollapse2);
			if (btnExpand1 != null && btnExpand2 != null) {
				int nSectionState = View.VISIBLE;
				if (btnExpand1.getText() == "Collapse All") {
					nSectionState = View.GONE;
					btnExpand1.setText("Expand All");
					btnExpand2.setText("Expand All");
				} else {
					btnExpand1.setText("Collapse All");
					btnExpand2.setText("Collapse All");
				}

				setSectionButtonState(
						(TextView) findViewById(R.id.txtOverview),
						(Button) findViewById(R.id.btnOverview), nSectionState);
				setSectionButtonState(
						(TextView) findViewById(R.id.txtDirections),
						(Button) findViewById(R.id.btnDirections),
						nSectionState);
				setSectionButtonState(
						(TextView) findViewById(R.id.txtDescription),
						(Button) findViewById(R.id.btnDescription),
						nSectionState);
				setSectionButtonState(
						(TextView) findViewById(R.id.txtConditions),
						(Button) findViewById(R.id.btnConditions),
						nSectionState);
				setSectionButtonState((TextView) findViewById(R.id.txtFees),
						(Button) findViewById(R.id.btnFees), nSectionState);
				setSectionButtonState(
						(TextView) findViewById(R.id.txtAmenities),
						(Button) findViewById(R.id.btnAmenities), nSectionState);
				setSectionButtonState((TextView) findViewById(R.id.txtMisc),
						(Button) findViewById(R.id.btnMisc), nSectionState);
			}
		}
	};
}
