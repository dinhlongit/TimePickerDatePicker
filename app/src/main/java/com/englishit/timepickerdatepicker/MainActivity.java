package com.englishit.timepickerdatepicker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView tvNgay,tvGio;
    EditText edCongViec;
    EditText edNoiDung;
    Button btnGio,btnNgay,btnAdd;
ArrayList<WorkInWeek> dsCongViec = new ArrayList<WorkInWeek>();
ArrayAdapter<WorkInWeek> arrayAdapter = null;
    ListView listView;
    Calendar cal;
    Date ngayHoanThanh;
    Date tgHoanThanh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        getDefaultTime();
        addEvent();
    }

    private void addControls() {
        tvNgay=(TextView) findViewById(R.id.tvNgay);
        tvGio=(TextView) findViewById(R.id.tvGio);
        edCongViec=(EditText) findViewById(R.id.edCongViec);
        edNoiDung=(EditText) findViewById(R.id.edNoiDung);
        btnNgay=(Button) findViewById(R.id.btnDate);
        btnGio=(Button) findViewById(R.id.btnGio);
        btnAdd=(Button) findViewById(R.id.btnThemCongViec);
        listView=(ListView) findViewById(R.id.lvcongviec);
        arrayAdapter = new ArrayAdapter<WorkInWeek>(MainActivity.this,android.R.layout.simple_list_item_1,dsCongViec);
        listView.setAdapter(arrayAdapter);

    }

    private void getDefaultTime() {
        cal = Calendar.getInstance(); // lay ngay hien tai cua he thong
        SimpleDateFormat sdf = null;
        //dinh dang ngay thang nam
        sdf =new SimpleDateFormat("dd/mm/YYY", Locale.getDefault());
        String strDay = sdf.format(cal.getTime());
        tvNgay.setText(strDay);

        //dinh dang gio
        sdf = new SimpleDateFormat("hh:mm a",Locale.getDefault());
        String strHour = sdf.format(cal.getTime());
        tvGio.setText(strHour);

        //lấy giờ theo 24 để lập trình theo Tag
        sdf=new SimpleDateFormat("HH:mm",Locale.getDefault());
        tvGio.setTag(sdf.format(cal.getTime()));

       // gán cal.getTime() cho ngày hoàn thành và giờ hoàn thành
        ngayHoanThanh=cal.getTime();
        tgHoanThanh=cal.getTime();
    }

    private void addEvent() {
        btnNgay.setOnClickListener(new xuLyButton());
        btnGio.setOnClickListener(new xuLyButton());
        btnAdd.setOnClickListener(new xuLyButton());
       listView.setOnItemClickListener(new xyLyListView());
        listView.setOnItemLongClickListener(new xuLyXoaListView());
    }


    private class xuLyButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == btnNgay){
                showDatePickerDialog();
            }
            if (v==btnGio){
                showTimePickerDialog();
            }
            if (v==btnAdd){
                processAddJob();
            }

        }
    }

    private void processAddJob() {
        if (edCongViec.getText().toString().isEmpty() || edNoiDung.getText().toString().isEmpty()){
            Toast.makeText(MainActivity.this,"Please Enter Content !", Toast.LENGTH_LONG).show();
        }else{
        String congViec = edCongViec.getText().toString();
        String noiDung = edNoiDung.getText().toString();
        WorkInWeek workInWeek = new WorkInWeek(congViec,noiDung,ngayHoanThanh,tgHoanThanh);
        dsCongViec.add(workInWeek);
        arrayAdapter.notifyDataSetChanged();
        edCongViec.setText("");
        edNoiDung.setText(""); }

    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                tvNgay.setText((dayOfMonth) +"/"+(month+1)+"/"+year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, month, dayOfMonth);
                ngayHoanThanh = cal.getTime();


            }
        };

        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=tvNgay.getText()+"";
        String strArrtmp[]=s.split("/");
        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(
                MainActivity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày hoàn thành");
        pic.show();
    }

    private void showTimePickerDialog() {
       TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
           @Override
           public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
               // Xu ly luu gio va AM PM
               String s = hourOfDay + ":" + minute;
               int hourTemp = hourOfDay;
               if (hourTemp > 12){
                   hourTemp -= 12;
                   tvGio.setText(hourTemp +":" + minute +" " + (hourOfDay > 12 ?"AM":"PM"));
                   tvGio.setTag(s);
                   //lưu vết lại giờ vào hourFinish
                   cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                   cal.set(Calendar.MINUTE, minute);
                   tgHoanThanh = cal.getTime();

               }


           };
       };
        //các lệnh dưới này xử lý ngày giờ trong TimePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=tvGio.getTag()+"";
        String strArr[]=s.split(":");
        int gio=Integer.parseInt(strArr[0]);
        int phut=Integer.parseInt(strArr[1]);
        TimePickerDialog time=new TimePickerDialog(
                MainActivity.this,
                callback, gio, phut, true);
        time.setTitle("Chọn giờ hoàn thành");
        time.show();
    }


    private class xyLyListView implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(MainActivity.this,dsCongViec.get(position).toString(),Toast.LENGTH_LONG).show();
        }



    }

    private class xuLyXoaListView implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            dsCongViec.remove(position);
            arrayAdapter.notifyDataSetChanged();
            return false;
        }
    }
}
