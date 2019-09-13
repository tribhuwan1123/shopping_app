package com.example.android.shop.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.esewa.android.sdk.payment.ESewaConfiguration;
import com.esewa.android.sdk.payment.ESewaPayment;
import com.esewa.android.sdk.payment.ESewaPaymentActivity;
import com.example.android.shop.Helper.CartHelper;
import com.example.android.shop.R;
import com.example.android.shop.adapter.CartAdapter;
import com.example.android.shop.model.Cart;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

public class CartFragment extends Fragment {

    List<Cart> cartList = new ArrayList<>();
    String pname;
    String pPrice="10";
    int pImage;
    int amount;
    private static final String CONFIG_ENVIRONMENT = ESewaConfiguration.ENVIRONMENT_TEST;
    private static final int REQUEST_CODE_PAYMENT = 1;
    private ESewaConfiguration eSewaConfiguration;

    private static final String MERCHANT_ID = "JB0BBQ4aD0UqIThFJwAKBgAXEUkEGQUBBAwdOgABHD4DChwUAB0R";
    private static final String MERCHANT_SECRET_KEY = "BhwIWQQADhIYSxILExMcAgFXFhcOBwAKBgAXEQ==";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_cart,container,false);

        eSewaConfiguration = new ESewaConfiguration()
                .clientId(MERCHANT_ID)
                .secretKey(MERCHANT_SECRET_KEY)
                .environment(CONFIG_ENVIRONMENT);



        Button buy= (Button) view.findViewById(R.id.buy);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvCart);
        TextView totalText=(TextView)view.findViewById(R.id.totalText);
//        Bundle bundle1= getArguments();
//            pname = bundle1.getString("name");
//            pPrice = bundle1.getString("price");
//             totalText.setText(pPrice);
       // amount = Integer.parseInt(totalText.getText().toString());
        // pImage = bundle.getInt("image");
        //byte img=(byte) pImage;
        //Bitmap b = BitmapFactory.decodeResource(getResources(),pImage);
        //System.out.println("vall "+b);
        //final Cart cart1= new Cart(pname,pPrice);
        final CartHelper cartHelper = new CartHelper(getApplicationContext(), null, null, 0);
       // cartHelper.insertCart(pname,pPrice);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        cartList=cartHelper.getAllProducts();
        CartAdapter cartAdapter = new CartAdapter(cartList);
        recyclerView.setAdapter(cartAdapter);


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePayment(pPrice);
            }
        });

    return view;
    }
    private void makePayment(String amount) {
        ESewaPayment eSewaPayment = new ESewaPayment(amount, "someProductName", "someUniqueId_" + System.nanoTime(), "https://somecallbackurl.com");
        Intent intent = new Intent(getActivity(), ESewaPaymentActivity.class);
        intent.putExtra(ESewaConfiguration.ESEWA_CONFIGURATION, eSewaConfiguration);
        intent.putExtra(ESewaPayment.ESEWA_PAYMENT, eSewaPayment);
        System.out.println("checkHit "+amount);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("checkHit "+ " herrrrr");
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == RESULT_OK) {
                String s = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                System.out.println("Proof of Payment"+ s);
                Toast.makeText(getActivity(), "SUCCESSFUL PAYMENT", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Canceled By User", Toast.LENGTH_SHORT).show();
            } else if (resultCode == ESewaPayment.RESULT_EXTRAS_INVALID) {
                String s = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                System.out.println("Proof of Payment"+ s);
            }
        }
    }
}
