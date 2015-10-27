package com.dist.iprocess.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.dist.iprocess.R;
import com.dist.iprocess.view.CalendarDayPickerView;
import com.dist.iprocess.view.CalendarMonthAdapter;

public class BaseView_CalendarActivity extends Activity implements com.dist.iprocess.view.CalendarDatePickerController {

    private CalendarDayPickerView dayPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_view_calendar_activity);

        dayPickerView = (CalendarDayPickerView) findViewById(R.id.pickerView);
        dayPickerView.setController(this);
    }

 

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getMaxYear()
    {
        return 2017;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day)
    {
        Log.e("Day Selected", day + " / " + month + " / " + year);
    }

    @Override
    public void onDateRangeSelected(CalendarMonthAdapter.SelectedDays<CalendarMonthAdapter.CalendarDay> selectedDays)
    {

        Log.e("Date range selected", selectedDays.getFirst().toString() + " --> " + selectedDays.getLast().toString());
    }
}
