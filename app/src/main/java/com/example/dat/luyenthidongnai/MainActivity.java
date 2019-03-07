package com.example.dat.luyenthidongnai;
import java.io.File;
import java.io.IOException;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.ResponseHandler;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
//import com.android.volley.Request;
import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.facebook.AccessToken;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.GraphRequest;
//import com.facebook.GraphResponse;
//import com.facebook.Profile;
//import com.facebook.login.Login;
//import com.facebook.login.LoginBehavior;
//import com.facebook.login.LoginManager;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;
//import com.facebook.login.widget.ProfilePictureView;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    WebView webViewltdn;
//    CallbackManager callbackManager;
//    LoginButton loginButton;
    Button loginButton1,logoutButton1;
//        logoutButton,
    TextView xinChao,userName;
    String
//            name, urlltdn ="https://luyenthidongnai.com",
            avatarURL,
            urlToParse,
//            a,
//            content,
            content1,
            removeUTFCt;
    View lyNavFooter;
    DrawerLayout drawer;
    de.hdodenhof.circleimageview.CircleImageView avatar;
//    RequestQueue requestQueue;
//    StringRequest stringRequest;
//    OkHttpClient client;

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static int FCR = 1;
    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (Build.VERSION.SDK_INT >= 21) {
            Uri[] results = null;

            //Check if response is positive
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == FCR) {

                    if (null == mUMA) {
                        return;
                    }
                    if (intent == null) {
                        //Capture Photo if no image available
                        if (mCM != null) {
                            results = new Uri[]{Uri.parse(mCM)};
                        }
                    } else {
                        String dataString = intent.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }
                }
            }
            mUMA.onReceiveValue(results);
            mUMA = null;
        } else {

            if (requestCode == FCR) {
                if (null == mUM) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUM.onReceiveValue(result);
                mUM = null;
            }
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        client = new OkHttpClient();
        lyNavFooter = (View) findViewById(R.id.lyNavFooter);
        logoutButton1 = (Button) findViewById(R.id.logout_button1);
//        callbackManager = CallbackManager.Factory.create();
        if (Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
        }
        webViewltdn = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webViewltdn.getSettings();
        webViewltdn.loadUrl("https://www.luyenthidongnai.com");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(0);
            webViewltdn.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= 19) {
            webViewltdn.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT < 19) {
            webViewltdn.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webViewltdn.setWebChromeClient(new WebChromeClient()
        {
            //For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {

                mUM = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                MainActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FCR);
            }

            // For Android 3.0+, above method not supported in some android 3+ versions, in such case we use this
            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {

                mUM = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                MainActivity.this.startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FCR);
            }

            //For Android 4.1+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {

                mUM = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                MainActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), MainActivity.FCR);
            }

            //For Android 5.0+
            public boolean onShowFileChooser(
                    WebView webView, ValueCallback<Uri[]> filePathCallback,
                    WebChromeClient.FileChooserParams fileChooserParams) {

                if (mUMA != null) {
                    mUMA.onReceiveValue(null);
                }

                mUMA = filePathCallback;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(MainActivity.this.getPackageManager()) != null) {

                    File photoFile = null;

                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCM);
                    } catch (IOException ex) {
                        Log.e(TAG, "Image file creation failed", ex);
                    }
                    if (photoFile != null) {
                        mCM = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }

                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("*/*");
                Intent[] intentArray;

                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooserIntent, FCR);

                return true;
            }
        });
//        webViewltdn.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");
        webViewltdn.setWebViewClient(new WebViewClient(){
        @Override
        public void onPageStarted (WebView view, String url, Bitmap favicon)
        {
            super.onPageStarted(view, url, favicon);
//            String htmlContent = getRemoteContent(url);
        }
        @Override
        public void onPageFinished (WebView view, String url){
            urlToParse = view.getUrl();
            Log.d("ABC","URL :" + urlToParse );
            if(Build.VERSION.SDK_INT >=19) {
                 webViewltdn.evaluateJavascript(
                         "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                         new ValueCallback<String>() {
                             @Override
                             public void onReceiveValue(String html) {
                                 Log.d("HTML", html);
                                 content1 = html;
                                 timPage();
                             }
                         });
            }
//                 if(urlToParse.contains("luyenthidongnai")) {
//                     webViewltdn.loadUrl("javascript:(function() { " +
//                             "document.getElementsByTagName('header')[0].style.display='none'; " +
//                             "document.getElementsByTagName('div')[6].style.display='none'; " +
//                             "document.getElementsByTagName('div')[7].style.display='none'; " +
//                             "})()");
//                 }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "L U Y E N T H I D O N G N A I", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
//        loginButton = (LoginButton) headerView.findViewById(R.id.login_button);
//        logoutButton = (Button) findViewById(R.id.logout_button);
        loginButton1 = (Button) headerView.findViewById(R.id.login_button1);
        avatar = (de.hdodenhof.circleimageview.CircleImageView)headerView.findViewById(R.id.avatar);
        xinChao = (TextView) headerView.findViewById(R.id.textViewXinChao);
        userName = (TextView) headerView.findViewById(R.id.textViewName);
        invisible();
//        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
//        setLogin_Button();
//        setLogout_Button();
        setLogin_Button1();
        setLogout_Button1();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    private File createImageFile() throws IOException {

        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    public static StringBuffer removeUTFCharacters(String data) {
        Pattern p = Pattern.compile("\\\\u(\\p{XDigit}{4})");
        Matcher m = p.matcher(data);
        StringBuffer buf = new StringBuffer(data.length());
        while (m.find()) {
            String ch = String.valueOf((char) Integer.parseInt(m.group(1), 16));
            m.appendReplacement(buf, Matcher.quoteReplacement(ch));
        }
        m.appendTail(buf);
        return buf;
    }

    public String onReceiveValue(String html) {
        return removeUTFCharacters(html).toString();
    }

    private void timPage(){
        int len = urlToParse.length();
        if(urlToParse.substring(len-4).equals("#_=_")){
            Log.d("LEN","substring :" +urlToParse.substring(len-4));
//            new GetUrl().execute(urlToParse);
//                Log.d("CON1","content :" + content);
//                if(content !=null) {
//                    setHinh();
//                }
            removeUTFCt = onReceiveValue(content1);
            Log.d("RMU","removedUTF :"+ removeUTFCt);
            Document document = Jsoup.parse(removeUTFCt);
            if( document != null){
                Element elementAvatar = document.getElementsByTag("img").first();
                    if(elementAvatar != null){
                        avatarURL = elementAvatar.attr("src");
                        int lenA = avatarURL.length();
                        Log.d("AVA","ava :" + avatarURL.substring(2,avatarURL.length()-2));
                        Picasso.get().load(avatarURL.substring(2,lenA-2)).into(avatar);
                    }

                Element elmName = document.getElementsByTag("button").get(1);
                if (elmName != null) {
                    String Name = elmName.text().replace("\\n","").trim();
                    Log.d("LOLN","name :" + Name);
                    userName.setText(Name);
                }

            }
        }
    }


    private void setLogout_Button1() {
        logoutButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webViewltdn.loadUrl("https://luyenthidongnai.com/logout");
                drawer.closeDrawers();
                logoutButton1.setVisibility(View.GONE);
                xinChao.setVisibility(View.INVISIBLE);
                userName.setVisibility(View.INVISIBLE);
                avatar.setVisibility(View.INVISIBLE);
                loginButton1.setVisibility(View.VISIBLE);
            }
        });
    }
    private void setLogin_Button1() {
        loginButton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                webViewltdn.loadUrl("javascript:(function(){"+
                        "l=document.getElementById('btnlogin');"+
                        "e=document.createEvent('HTMLEvents');"+
                        "e.initEvent('click',true,true);"+
                        "l.dispatchEvent(e);"+
                        "})()");
                drawer.closeDrawers();
                loginButton1.setVisibility(View.INVISIBLE);
                xinChao.setVisibility(View.VISIBLE);
                userName.setVisibility(View.VISIBLE);

                avatar.setVisibility(View.VISIBLE);
                logoutButton1.setVisibility(View.VISIBLE);


            }
        });
    }


    private void invisible(){
        avatar.setVisibility(View.INVISIBLE);
        xinChao.setVisibility(View.INVISIBLE);
        userName.setVisibility(View.INVISIBLE);
        logoutButton1.setVisibility(View.GONE);
//        lyNavFooter.setVisibility(View.GONE);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            webViewltdn.loadUrl("https://www.luyenthidongnai.com/thao-luan");
        } else if (id == R.id.nav_gallery) {
            webViewltdn.loadUrl("https://www.luyenthidongnai.com/kiem-tra");
        } else if (id == R.id.nav_slideshow) {
            webViewltdn.loadUrl("https://www.luyenthidongnai.com/giai-tri");
//        } else if (id == R.id.nav_manage) {
//            webViewltdn.loadUrl("https://www.luyenthidongnai.com/quy-dinh");
//        } else if (id == R.id.nav_gioi_thieu) {
//            webViewltdn.loadUrl("https://www.luyenthidongnai.com/gioi-thieu");
//        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK)&& webViewltdn.canGoBack()) {
            webViewltdn.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


//    private class GetUrl extends AsyncTask<String,String,String>{
//        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
//
//    @Override
//    protected String doInBackground(String... strings) {
//        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
//        builder.url(strings[0]);
//        okhttp3.Request request = builder.build();
//        try {
//            okhttp3.Response response = okHttpClient.newCall(request).execute();
//            return response.body().string();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    @Override
//    protected void onPostExecute(String s){
//        if(!s.equals("")){
//            content = s;
//        }
//        super.onPostExecute(s);
//    }
//
//    }
    //        requestQueue = Volley.newRequestQueue(this);
//        stringRequest = new StringRequest(Request.Method.GET, urlltdn, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Document document = Jsoup.parse(response);
//                Log.d("TCR","response :" + response);
//
//                if( document != null){
//                    Elements imgs = document.select("img");
//                    for (Element element : imgs){
//                        Element elementAvatar = element.getElementsByTag("img").first();
//                        if(elementAvatar != null){
//                            avatarURL = elementAvatar.attr("src");
//                            xinChao.setText(avatarURL.substring(0,20));
//                        break;
//                        }
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(stringRequest);
//        try {
//            a = run(urlToParse);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Log.d("ACCB","res :" + a);

    //    private String getRemoteContent(String url)
//    {
//        HttpGet pageGet = new HttpGet(url);
//        HttpClient client = new DefaultHttpClient();
//
//        ResponseHandler<String> handler = new ResponseHandler<String>()
//        {
//            public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException
//            {
//                HttpEntity entity = response.getEntity();
//                String html;
//
//                if (entity != null)
//                {
//                    html = EntityUtils.toString(entity);
//                    return html;
//                }
//                else
//                {
//                    return null;
//                }
//            }
//        };
//
//        String pageHTML = null;
//        try
//        {
//            pageHTML = client.execute(pageGet, handler);
//            //if you want to manage http sessions then you have to add localContext as a third argument to this method and have uncomment below line to sync cookies.
//            //syncCookies();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        // you can filter your html content here if you wish before displaying
//        // in webview
//
//        return pageHTML;
//    }
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private String getRemoteContent(String url){
//        run(url);
//    }

//    public String run(String url) throws IOException{
//        okhttp3.Request request = new okhttp3.Request.Builder()
//                .url(url).build();
//        try (okhttp3.Response response = client.newCall(request).execute()) {
//            return response.body().string();
//        }
//    }

    //    private void setLogin_Button() {
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                loginButton.setVisibility(View.INVISIBLE);
//
//                xinChao.setVisibility(View.VISIBLE);
//                userName.setVisibility(View.VISIBLE);
//                logoutButton.setVisibility(View.VISIBLE);
//                lyNavFooter.setVisibility(View.VISIBLE);
//                result();
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//            }
//        });
//    }

//    private void result() {
//        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject object, GraphResponse response) {
//                Log.d("JSON",response.getJSONObject().toString());
//                try {
//                    name = object.getString("name");
//
//                    userName.setText(name);
//                }
//                catch (JSONException e){
//                    e.printStackTrace();
//                }
//            }
//        });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "name,email,first_name");
//        graphRequest.setParameters(parameters);
//        graphRequest.executeAsync();
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }

    //    private void setLogout_Button() {
//        logoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LoginManager.getInstance().logOut();
//                logoutButton.setVisibility(View.GONE);
//                lyNavFooter.setVisibility(View.GONE);
//
//                xinChao.setVisibility(View.INVISIBLE);
//                userName.setVisibility(View.INVISIBLE);
//                userName.setText("");
//
//                loginButton.setVisibility(View.VISIBLE);
//            }
//        });
//    }

    //    private class MyJavaScriptInterface
//    {
//        private Context ctx;
//
//        MyJavaScriptInterface(Context ctx) {
//            this.ctx = ctx;
//        }
//
//        public void showHTML(String html) {
//            new AlertDialog.Builder(ctx).setTitle("HTML").setMessage(html)
//                    .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();
//        }
//    }

    //    private void setHinh (){
//
//    }

}

