package com.example.android.shop.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.android.shop.R;
import com.example.android.shop.model.Cart;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class CartHelper extends SQLiteOpenHelper {

    static String DB_NAME = "Cart";
    static int version=1;
    List<Cart> cartList=new ArrayList<>();

    public CartHelper(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, CartHelper.version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CART(ID INTEGER PRIMARY KEY AUTOINCREMENT,PRODUCT_NAME TEXT,PRICE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Long insertCart(String name,String price)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        //byte[] data = getBitmapAsByteArray(image);
        contentValues.put("PRODUCT_NAME",name);
        contentValues.put("PRICE",price);
        //contentValues.put("IMAGE",getBytes(cart.getImageCart()));
        Long id=sqLiteDatabase.insert("CART",null, contentValues);

        Log.v("insert","Data inserted "+id);
        return id;
    }

//    public static byte[] getBytes(Bitmap bitmap) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
//        return stream.toByteArray();
//    }



    public List<Cart> getAllProducts()
    {
        SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM CART",null);
        List<Cart> productList= new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do {
                Cart cart= new Cart();
                cart.setpName(cursor.getString(cursor.getColumnIndex("PRODUCT_NAME")));
                cart.setpPrice(cursor.getString(cursor.getColumnIndex("PRICE")));
    //            byte[] image = cursor.getBlob(cursor.getColumnIndex("IMAGE"));
  //              Bitmap  bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
//                cart.setImageCart(bitmap);
                cartList.add(cart);

            }while (cursor.moveToNext());

        }
        return cartList;

    }

}
