/*
package com.example.mystreamer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import com.example.mystreamer.encrypteplayer.EncryptedFileDataSourceFactory;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import android.os.Bundle;

public class EncryptActivity extends AppCompatActivity {
    private static final String AES_ALGORITHM = "AES";
    private static final String AES_TRANSFORMATION = "AES/CTR/NoPadding";
    private static final String PASSWORD = "1133";
    private static final byte[]SALT = {3, (byte) 253, (byte) 245, (byte) 149,86, (byte) 148, (byte) 148,43};
    private static final byte[]IV = {(byte) 139, (byte) 214,102,1, (byte) 150, (byte) 134, (byte) 236, (byte) 182,89,110,20,55, (byte) 243,120,76, (byte) 182};
    private static final String HASH_KEY = "SHA-256";

    private Cipher cipher;
    private SecretKeySpec secretKeySpec;
    private IvParameterSpec ivParameterSpec;




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        //initial Cipher

        try {

            secretKeySpec = generate(PASSWORD,SALT,HASH_KEY,AES_ALGORITHM);

            ivParameterSpec = new IvParameterSpec(IV);

            cipher =  createCipher(ivParameterSpec,secretKeySpec,AES_TRANSFORMATION);

        } catch (Exception e) {
            e.printStackTrace();
        }


        //initial EncryptedDataSource in ExoPlayer

        DataSource.Factory encryptedFileDataSource  = new EncryptedFileDataSourceFactory(cipher,secretKeySpec,ivParameterSpec,new DefaultBandwidthMeter());
    }

    //region Cipher
    private Cipher createCipher(IvParameterSpec mIvParameterSpec, SecretKeySpec mSecretKeySpec,String transformation) throws Exception {
        Cipher mCipher = Cipher.getInstance(transformation);
        mCipher.init(Cipher.DECRYPT_MODE, mSecretKeySpec, mIvParameterSpec);
        return mCipher;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private SecretKeySpec generate(String password, byte[] salt, String hashKey, String algoritm) throws Exception {
        MessageDigest md = MessageDigest.getInstance(hashKey);
        md.update(salt);
        byte[] key = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(key, algoritm);
    }
    //endregion
}
*/
