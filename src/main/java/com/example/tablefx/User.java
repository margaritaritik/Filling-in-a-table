package com.example.tablefx;

//класс для формирования данных, с помощью которых будет заполнена таблица(в данном случае два атрибута:id и text)
public class User {
    private String Extension;
    private String SizeFile;
    private Object Content;
    private Object HeightImg;
    private Object WidthImg;
    private Object Countbit;

    public User(String Extension, String SizeFile, Object Content,Object HeightImg,Object WidthImg,Object Countbit) {
        this.Extension = Extension;
        this.SizeFile=SizeFile;
        this.Content = Content;
        this.HeightImg=HeightImg;
        this.WidthImg=WidthImg;
        this.Countbit=Countbit;
    }
    public void SetExtension(String value) { this.Extension = value; }
    public String getExtension() { return Extension; }

    public void SetSizeFile(String value) { this.SizeFile = value; }
    public String getSizeFile() { return SizeFile; }


    public void SetContent(Object value) { this.Content = value; }
    public Object getContent() { return Content; }

    public void SetHeightImg(Object value) { this.HeightImg = value; }
    public Object getHeightImg() { return HeightImg; }

    public void SetCountbit(Object value) { this.Countbit = value; }
    public Object getCountbit() { return Countbit; }

    public void SetWidthImg(Object value) { this.WidthImg = value; }
    public Object getWidthImg() { return WidthImg; }
}

