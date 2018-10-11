package com.englishit.timepickerdatepicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WorkInWeek {
    private  String tenCongViec;
    private  String moTa;
    private Date ngayHoanThanh;
    private  Date tgHoanThanh;


    public WorkInWeek() {
        super();
    }

    public WorkInWeek(String tenCongViec, String moTa, Date ngayHoanThanh, Date tgHoanThanh) {
        this.tenCongViec = tenCongViec;
        this.moTa = moTa;
        this.ngayHoanThanh = ngayHoanThanh;
        this.tgHoanThanh = tgHoanThanh;
    }

    public String getTenCongViec() {
        return tenCongViec;
    }

    public void setTenCongViec(String tenCongViec) {
        this.tenCongViec = tenCongViec;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Date getNgayHoanThanh() {
        return ngayHoanThanh;
    }

    public void setNgayHoanThanh(Date ngayHoanThanh) {
        this.ngayHoanThanh = ngayHoanThanh;
    }

    public Date getTgHoanThanh() {
        return tgHoanThanh;
    }

    public void setTgHoanThanh(Date tgHoanThanh) {
        this.tgHoanThanh = tgHoanThanh;
    }
    public String getDateFormat(Date d)
    {
        SimpleDateFormat dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dft.format(d);
    }

    public String getHourFormat(Date d)
    {
        SimpleDateFormat dft=new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return dft.format(d);
    }



    @Override
    public String toString() {
        return tenCongViec + "-" + moTa + "-" + getDateFormat(ngayHoanThanh) + "-" +getHourFormat(tgHoanThanh);
    }
}
