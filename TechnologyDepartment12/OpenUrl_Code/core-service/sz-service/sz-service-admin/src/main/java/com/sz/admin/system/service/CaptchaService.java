package com.sz.admin.system.service;

import jakarta.servlet.http.HttpServletRequest;
import com.sz.core.common.entity.SliderPuzzle;
import com.sz.core.common.entity.CheckPuzzle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public interface CaptchaService {

    SliderPuzzle getImageCode(HttpServletRequest request);

    void checkImageCode(CheckPuzzle checkPuzzle) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException;
}
