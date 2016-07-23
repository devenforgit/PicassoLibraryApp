package pe.edu.utp.picassoapp;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;

public class MainActivity extends ActionBarActivity
        implements View.OnClickListener,
        Callback
{

    final String URL = "http://goo.gl/rUI7RF";
    ImageView imageView;
    ImageView imageViewv;
    TextView txtDesc;
    TextView txtCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListeners();
    }

    private void setListeners() {
        imageView = (ImageView) findViewById(R.id.imgImage);
        imageViewv = (ImageView) findViewById(R.id.imgImagev);
        txtDesc = (TextView) findViewById(R.id.txtDesc);
        txtCourse = (TextView) findViewById(R.id.txtCourse);

        findViewById(R.id.btnDownload).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //esto agrega elementos a la barra de acción si está presente .
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnDownload:
                downloadImage();

                break;
        }
    }

    private void downloadImage() {
        try {

            //
            Picasso.with(this)
                    .load("http://goo.gl/rUI7RF").rotate(0)
                    .resize(300, 300)
                    .into(imageView, this);
            Picasso.with(this).load("http://goo.gl/CLhqvU")
                    .resize(300, 300)
                    .into(imageViewv, this);

            // OR this one
            // Picasso.with(this).load("http://bit.ly/1DU2Zka").rotate(180).into(imageView, this);
            // Picasso.with(this).load("http://goo.gl/rUI7RF").rotate(0).into(target);

        } catch (Exception e) {
            downloadFailed();
        }
    }

    private void downloadFailed() {
        txtDesc.setText(getString(R.string.download_failed));
    }
    private Target target = new Target() {

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            imageView.setImageBitmap(bitmap);
            imageViewv.setImageBitmap(bitmap);
            downloadSucceed();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            downloadFailed();
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            if (placeHolderDrawable != null) {
                imageView.setImageDrawable(placeHolderDrawable);
                imageViewv.setImageDrawable(placeHolderDrawable);
            }

        }
    };

    private void downloadSucceed() {
        txtDesc.setText(getString(R.string.download_succeed));
    }

    @Override
    public void onSuccess() {
        downloadSucceed();
    }

    @Override
    public void onError() {
        downloadFailed();
    }
}
