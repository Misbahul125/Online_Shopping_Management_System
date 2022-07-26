/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.helper;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author Misbahul Haque
 */
public class ResponseHelper {

    public ResponseHelper() {
    }

    public void sendTrueResponse(HttpServletResponse response , String message) throws IOException {

        JSONObject jSONObject = new JSONObject();
        jSONObject.put("success", true);
        jSONObject.put("message", message);
        response.setStatus(200);
        response.getWriter().append(jSONObject.toString());
        response.getWriter().close();

    }
    
    public void sendOrderPlacedResponse(HttpServletResponse response , String orderId , String message) throws IOException {

        JSONObject jSONObject = new JSONObject();
        jSONObject.put("success", true);
        jSONObject.put("orderId", orderId);
        jSONObject.put("message", message);
        response.setStatus(200);
        response.getWriter().append(jSONObject.toString());
        response.getWriter().close();

    }
    
    public void sendFalseResponse(HttpServletResponse response , String message) throws IOException {

        JSONObject jSONObject = new JSONObject();
        jSONObject.put("success", false);
        jSONObject.put("message", message);
        response.setStatus(500);
        response.getWriter().append(jSONObject.toString());
        response.getWriter().close();

    }
}