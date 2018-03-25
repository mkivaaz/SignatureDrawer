package kivaaz.com.signaturedrawer;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import kivaaz.com.signaturelibrary.Signature.Signature.SignatureView;

public class MainActivity extends AppCompatActivity {

    private SignatureView signatureView;
    private Button clearBtn, showBitmap;
    private ImageView sigBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clearBtn = findViewById(R.id.clearBtn);
        showBitmap = findViewById(R.id.showBitmapBtn);
        sigBitmap = findViewById(R.id.bitmapImg);
        signatureView = findViewById(R.id.signatureView);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        signatureView.setPenColor(Color.parseColor("#B34CF3"));// Set Pen Color
        signatureView.setCanvasColor(Color.WHITE);// Set Pen Color
        signatureView.setStrokeWidth(12);// Set Pen Stroke Size
        signatureView.setupDrawing(metrics);

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signatureView != null){
                    signatureView.clearCanvas();// Clear Canvas
                }
            }
        });

        showBitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signatureView != null){
                    sigBitmap.setImageBitmap(signatureView.getCanvasBitmap());// Get Bitmap from Canvas and Display
                    sigBitmap.setVisibility(View.VISIBLE);
                    signatureView.setVisibility(View.GONE);
                }

            }
        });
    }
}
