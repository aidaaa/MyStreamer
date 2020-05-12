package com.example.mystreamer.encrypteplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.mystreamer.R;
import com.example.mystreamer.encrypteplayer.EncryptedFileDataSourceFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import android.os.Bundle;

public class EncryptActivity extends AppCompatActivity {
    private static final String AES_ALGORITHM = "AES";
    private static final String AES_TRANSFORMATION = "AES/CTR/NoPadding";
    private static final String PASSWORD = "1133";
    private static final byte[] SALT = {3, (byte) 253, (byte) 245, (byte) 149, 86, (byte) 148, (byte) 148, 43};
    private static final byte[] IV = {(byte) 139, (byte) 214, 102, 1, (byte) 150, (byte) 134, (byte) 236, (byte) 182, 89, 110, 20, 55, (byte) 243, 120, 76, (byte) 182};
    private static final String HASH_KEY = "SHA-256";

  //  private static final String fileToBeCrypted = "D:\\encrypt\\encrypt.mpg";
   // private static final String fileToBeDecrypted = "D:\\encrypt\\encrypt.mpg.crypt";
   // private static final String fileDecryptedOutput = "D:\\encrypt\\encrypt.mpg.decrypted";


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        File  fileToBeCrypted = new File(this.getExternalFilesDir(null), "encrypt.mpg");
        File  fileToBeDecrypted = new File(this.getExternalFilesDir(null), "encrypt.mpg.crypt");
        File  fileDecryptedOutput = new File(this.getExternalFilesDir(null), "encrypt1.mpg");

        try {
            //encryptfile(fileToBeCrypted, PASSWORD);
            decrypt(fileToBeDecrypted, PASSWORD, fileDecryptedOutput);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } /*catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }*/
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void encryptfile(File path, String password) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException
    {
        FileInputStream fis = new FileInputStream(path);
        FileOutputStream fos = new FileOutputStream(path.getPath().concat(".crypt"));

        MessageDigest sha = MessageDigest.getInstance(HASH_KEY);

        sha.update(SALT);

        sha.update(password.getBytes(StandardCharsets.US_ASCII));

        byte[] key=sha.digest();

        SecretKeySpec sks = new SecretKeySpec(key, AES_ALGORITHM);

        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);
        cipher.init(Cipher.ENCRYPT_MODE,sks,ivParameterSpec);

        CipherOutputStream cos = new CipherOutputStream(fos, cipher);
        int b;
        byte[] d = new byte[8];
        while((b = fis.read(d)) != -1) {
            cos.write(d, 0, b);
        }
        cos.flush();
        cos.close();
        fis.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void decrypt(File path, String password, File outPath) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException
    {
        FileInputStream fis = new FileInputStream(path);
        FileOutputStream fos = new FileOutputStream(outPath);

        MessageDigest sha = MessageDigest.getInstance(HASH_KEY);

        sha.update(SALT);

        sha.update(password.getBytes(StandardCharsets.US_ASCII));


        byte[] key=sha.digest();

        SecretKeySpec sks = new SecretKeySpec(key, AES_ALGORITHM);

        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);
        cipher.init(Cipher.DECRYPT_MODE,sks,ivParameterSpec);

        CipherInputStream cis = new CipherInputStream(fis, cipher);
        int b;
        byte[] d = new byte[8];
        while((b = cis.read(d)) != -1) {
            fos.write(d, 0, b);
        }
        fos.flush();
        fos.close();
        cis.close();
    }
    private void setPlayer()
    {
       /* File  fileDecryptedOutput = new File(this.getExternalFilesDir(null), "encrypt1.mpg");
        Uri uri=Uri.fromFile(fileDecryptedOutput);

        TrackSelector trackSelector=new DefaultTrackSelector();

        SimpleExoPlayer simpleExoPlayer= ExoPlayerFactory.newSimpleInstance(this,
                trackSelector);

        DataSource.Factory factory=new DefaultDataSourceFactory(this, Util.getUserAgent(this,"EXOPlayer"));

        MediaSource mediaSource=new ProgressiveMediaSource
                .Factory(factory).createMediaSource(uri);

        pw.setPlayer(simpleExoPlayer);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);

        simpleExoPlayer.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onPlayerError(ExoPlaybackException error) {
                System.out.println(error.getMessage());
            }
        });*/
    }
}
