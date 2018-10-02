package com.namkit.namki.novafolio.Wakeup;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.namkit.namki.novafolio.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Wakeup extends AppCompatActivity {
    private static final int MY_PERMISSION_CAMERA = 1111;
    private static final int REQUEST_TAKE_PHOTO = 2222;
    private static final int REQUEST_TAKE_ALBUM = 3333;
    private static final int REQUEST_IMAGE_CROP = 4444;
    private final int GALLERY_CODE = 1112;
    ImageView wake_view;

    // 갤러리에서 사진 가져오기-------------------------------------
    private void selectGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case GALLERY_CODE:
                    sendPicture(data.getData());
            }
        }
    }

    private void sendPicture(Uri imgUri) {

        String imagePath = getRealPathFromURI(imgUri); // path 경로
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
        wake_view.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
        System.out.println(rotate(bitmap,exifDegree));

    }
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }
    private Bitmap rotate(Bitmap src, float degree) {

// Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }
    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }




    GridView gridView; //그리드뷰
    Wakeup_Adapter adapter; //어뎁터
    Button btn_capture, btn_album;
    ImageView iv_view;

    String mCurrentPhotoPath;

    Uri imageUri;
    Uri photoURI, albumURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wakeup);

        gridView = (GridView) findViewById(R.id.gridView);
        wake_view = (ImageView)findViewById(R.id.wake_view);
        adapter = new Wakeup_Adapter();
        addarray();

        adapter.addItem(new Wakeup_Item(R.drawable.creenshot2));
        adapter.addItem(new Wakeup_Item(R.drawable.creenshot3));
        adapter.addItem(new Wakeup_Item(R.drawable.creenshot4));
        adapter.addItem(new Wakeup_Item(R.drawable.creenshot5));
        adapter.addItem(new Wakeup_Item(R.drawable.creenshot1));
       /* adapter.addItem(new Wakeup_Item(R.drawable.creenshot2));
        adapter.addItem(new Wakeup_Item(R.drawable.creenshot3));
        adapter.addItem(new Wakeup_Item(R.drawable.creenshot4));
        adapter.addItem(new Wakeup_Item(R.drawable.creenshot5));*/

        gridView.setAdapter(adapter);


     /*   gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                SingerItem item = (SingerItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getName(), Toast.LENGTH_LONG).show();
            }
        });*/

        // btn_capture = (Button) findViewById(R.id.btn_capture);
        btn_album = (Button) findViewById(R.id.btn_album);
        iv_view = (ImageView) findViewById(R.id.wake_view);

        /*btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureCamera();
            }
        });*/

        btn_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getAlbum();
            }
        });


        //checkPermission();
    }

    private void addarray() {
        //for(int i=0; i<10; i++) {
        try {
            ImageView imageView = null;
            URL url = new URL("이미지 주소");
            URLConnection conn = url.openConnection();
            conn.connect();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            Bitmap bm = BitmapFactory.decodeStream(bis);
            bis.close();
            imageView.setImageBitmap(bm);

            adapter.addItem(new Wakeup_Item(imageView));
        } catch (Exception e) {
        }
        //}
    }

    @Override
    public void onPause() {
        super.onPause();
        //카메라 사용하기
    }

    class Wakeup_Adapter extends BaseAdapter {
        ArrayList<Wakeup_Item> items = new ArrayList<Wakeup_Item>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Wakeup_Item item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            Wakeup_ItemView view = new Wakeup_ItemView(getApplicationContext());

            Wakeup_Item item = items.get(position);
      /*  view.setName(item.getName());
        view.setMobile(item.getMobile());
        view.setAge(item.getAge());*/
            view.setImage(item.getResId());

            int numColumns = gridView.getNumColumns();
            int rowIndex = position / numColumns;
            int columnIndex = position % numColumns;

            return view;
        }
    }

}
   /* private void captureCamera(){
        String state = Environment.getExternalStorageState();
        // 외장 메모리 검사
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Log.e("captureCamera Error", ex.toString());
                }
                if (photoFile != null) {
                    // getUriForFile의 두 번째 인자는 Manifest provier의 authorites와 일치해야 함

                    Uri providerURI = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    imageUri = providerURI;

                    // 인텐트에 전달할 때는 FileProvier의 Return값인 content://로만!!, providerURI의 값에 카메라 데이터를 넣어 보냄
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);

                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        } else {
            Toast.makeText(this, "저장공간이 접근 불가능한 기기입니다", Toast.LENGTH_SHORT).show();
            return;
        }
    }*/

  /*  public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "Camera");

        if (!storageDir.exists()) {
            Log.i("mCurrentPhotoPath1", storageDir.toString());
            storageDir.mkdirs();
        }

        imageFile = new File(storageDir, imageFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;
    }


    private void getAlbum(){
        Log.d("getAlbum", "getAlbum() 메서드 진입");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
        Log.d("getAlbum", "getAlbum() 메서드 탈출");
    }

    private void galleryAddPic(){
                    Log.d("galleryAddPic", "galleryAddPic() 메서드 진입");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // 해당 경로에 있는 파일을 객체화(새로 파일을 만든다는 것으로 이해하면 안 됨)
        File f = new File(mCurrentPhotoPath);
                    Log.d("galleryAddPic", "File f = new File(mCurrentPhotoPath); 진입");
        Uri contentUri = Uri.fromFile(f);
                    Log.d("galleryAddPic", "Uri contentUri = Uri.fromFile(f); 진입");
        mediaScanIntent.setData(contentUri);
                    Log.d("galleryAddPic", "mediaScanIntent.setData(contentUri); 진입");
        sendBroadcast(mediaScanIntent);
                    Log.d("galleryAddPic", " sendBroadcast(mediaScanIntent); 진입");
        Toast.makeText(this, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    // 카메라 전용 크랍
    public void cropImage(){
        Log.d("cropImage", "cropImage() 메서드 진입");
        Log.i("cropImage", "photoURI : " + photoURI + " / albumURI : " + albumURI);

        Intent cropIntent = new Intent("com.android.camera.action.CROP");
//.setImageUri(photoURI);
        // 50x50픽셀미만은 편집할 수 없다는 문구 처리 + 갤러리, 포토 둘다 호환하는 방법
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.setDataAndType(photoURI, "image/*");
        //cropIntent.putExtra("outputX", 200); // crop한 이미지의 x축 크기, 결과물의 크기
        //cropIntent.putExtra("outputY", 200); // crop한 이미지의 y축 크기
        cropIntent.putExtra("aspectX", 1); // crop 박스의 x축 비율, 1&1이면 정사각형
        cropIntent.putExtra("aspectY", 1); // crop 박스의 y축 비율
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("output", albumURI); // 크랍된 이미지를 해당 경로에 저장
        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }
    public String getPathFromUri(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null );
        cursor.moveToNext();
        String path = cursor.getString( cursor.getColumnIndex( "_data" ) );
        cursor.close();

        return path;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult", "onActivityResult() 메서드 진입");
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                                        Log.d("onActivityResult", "REQUEST_TAKE_PHOTO에서 RESULT_OK눌렀을때 진입");
                        galleryAddPic();
                                        Log.d("onActivityResult", "REQUEST_TAKE_PHOTO에서 RESULT_OK누르고 galleryAddPic() 메서드 다음");

                        iv_view.setImageURI(imageUri);
                                        Log.d("  onActivityResult", "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@imageUri 는 "+imageUri);
                                        Log.d("  onActivityResult", "albumURI 는"+albumURI);
                    } catch (Exception e) {
                                         Log.e("REQUEST_TAKE_PHOTO", e.toString());
                    }
                                          Log.d("onActivityResult", "adapter.addItem 진입");
                    //adapter.addItem(new Wakeup_Item(Integer.parseInt(getPathFromUri(imageUri))));
                    adapter.addItem(new Wakeup_Item(R.id.wake_view));
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(Wakeup.this, "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            case REQUEST_TAKE_ALBUM:
                if (resultCode == Activity.RESULT_OK) {

                    if(data.getData() != null){
                        try {
                            File albumFile = null;
                            albumFile = createImageFile();
                            photoURI = data.getData();
                            albumURI = Uri.fromFile(albumFile);
                            cropImage();
                        }catch (Exception e){
                            Log.e("TAKE_ALBUM_SINGLE ERROR", e.toString());
                        }
                    }
                }
                break;

            case REQUEST_IMAGE_CROP:
                Log.d("onActivityResult", "onActivityResult() 메서드 안에 케이스 : REQUEST_IMAGE_CROP 진입");

                Log.d("onActivityResult", "iv_view.setImageURI(albumURI); "+albumURI);
                if (resultCode == Activity.RESULT_OK) {
                    galleryAddPic();

                  //  String path = "wwww";
                  //  Uri.parse(path);
                    try {
                        iv_view.setImageURI(albumURI);
                       // Glide.with(this).load("http://www.selphone.co.kr/homepage/img/team/3.jpg").into(imageView);
                    }catch (NullPointerException e){
                        Log.d("onActivityResult", "e.printStackTrace(); 진입");
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) ||
                    (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))) {
                new AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                Log.d("경고 알아보자","Uri.parse(\"package:\" + getPackageName())"+Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSION_CAMERA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_CAMERA:
                for (int i = 0; i < grantResults.length; i++) {
                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                    if (grantResults[i] < 0) {
                        Toast.makeText(Wakeup.this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면 이 부분에서..

                break;
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        //Toast.makeText(this, "onDestroy호출", Toast.LENGTH_SHORT).show();
    }

}*/
/**
[로직]
        권한 및 카메라, 앨범을 정상적으로 가져왔을 경우를 가정한다.
        카메라 : 권한 검사 -> 파일 생성(빈 껍데기) -> 생성된 파일의 경로(Uri)와 함께 카메라 실행 -> 해당 경로에 사진찍은 이 후 데이터를 저장 -> 동기화 -> 이미지뷰에 뿌림
        앨범 : 권한 검사 -> 앨범 호출 -> 앨범에서 가져온 데이터 Uri값을 가져옴 -> 해당 Uri와 크롭할 이미지를 저장할 파일을 생성 및 경로를 설정함 ->
        Crop -> 이미지뷰에 뿌림

        [참고]
        captureCamera()에서 사용하는 FileProvider.getUriForFile함수가 contentURI를 얻는 방법이고
        createImageFile()에서 imageFile을 new File 할 때 중간에 들어가는 gyeom은 프로바이더에 들어가는 path랑 동일하게 작성해야한다.
        new File은 해당 위치의 파일의 경로를 객체화 시킨다. 만약 그 위치에 파일이 없으면 생성하는 것도 포함되나 가끔 파일을 못찾는다는 에러가 있는 기기 때문에 mkdir()을 통해 해당 파일의 바로 상위폴더를 먼저 생성하고 createNewFile()로 확실히 생성시킨다.

        ** 크롭 관련
                위의 로직은 사진 찍고 -> 저장 -> 크롭하고 -> 저장의 두 번 저장하는 방식이다. (파일 2개 생성)
                만약 사진을찍고 해당 사진을 크롭한것을 최종적으로 저장하고 싶다면 소스가 바뀐다. (파일 1개 생성)*/

    // 카메라 전용 크랍(앨범엔 크롭된 이미지만 저장시키기 위해)
    /*public void cropSingleImage(Uri photoUriPath){
        Log.i("cropSingleImage", "Call");
        Log.i("cropSingleImage", "photoUriPath : " + photoUriPath);

        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        // 50x50픽셀미만은 편집할 수 없다는 문구 처리 + 갤러리, 포토 둘다 호환하는 방법, addFlags로도 에러 나서 setFlags
        // 누가 버전 처리방법임
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        cropIntent.setDataAndType(photoUriPath, "image*//*");
        //cropIntent.putExtra("outputX", 200); // crop한 이미지의 x축 크기, 결과물의 크기
        //cropIntent.putExtra("outputY", 200); // crop한 이미지의 y축 크기
        cropIntent.putExtra("aspectX", 1); // crop 박스의 x축 비율, 1&1이면 정사각형
        cropIntent.putExtra("aspectY", 1); // crop 박스의 y축 비율
        Log.i("cropSingleImage", "photoUriPath22 : " + photoUriPath);


        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("output", photoUriPath); // 크랍된 이미지를 해당 경로에 저장



        // 같은 photoUriPath에 저장하려면 아래가 있어야함(왜지)
        List

                list = getPackageManager().queryIntentActivities(cropIntent, 0);
        grantUriPermission(list.get(0).activityInfo.packageName, photoUriPath,Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Intent i = new Intent(cropIntent);
        ResolveInfo res = list.get(0);
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        grantUriPermission(res.activityInfo.packageName, photoUriPath,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

        startActivityForResult(i, REQUEST_IMAGE_CROP);
    }*/
//이미지 돌아가면?  ImageView rotate 구글링