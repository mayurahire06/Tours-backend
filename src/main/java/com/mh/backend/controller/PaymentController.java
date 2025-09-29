//It is working
//package com.mh.backend.controller;
//
//import com.razorpay.Order;
//import com.razorpay.RazorpayClient;
//import com.razorpay.RazorpayException;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/payment")
//@CrossOrigin(origins = "http://localhost:5173") // Adjust to your frontend port
//public class PaymentController {
//
//    @Value("${razorpay.key_id}")
//    private String razorpayKeyId;
//
//    @Value("${razorpay.key_secret}")
//    private String razorpayKeySecret;
//
//    @PostMapping("/order")
//    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> data) {
//        try {
//            if (!data.containsKey("amount") || !(data.get("amount") instanceof Number)) {
//                Map<String, Object> error = new HashMap<>();
//                error.put("error", "Invalid or missing amount");
//                return ResponseEntity.badRequest().body(error);
//            }
//
//            RazorpayClient client = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
//
//            JSONObject orderRequest = new JSONObject();
//            orderRequest.put("amount", ((Number) data.get("amount")).longValue());
//            orderRequest.put("currency", "INR");
//            orderRequest.put("payment_capture", 1);
//
//            Order order = client.orders.create(orderRequest);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("id", order.get("id"));
//            response.put("amount", order.get("amount"));
//            response.put("currency", order.get("currency"));
//            response.put("status", order.get("status"));
//
//            return ResponseEntity.ok(response);
//        } catch (RazorpayException e) {
//            Map<String, Object> error = new HashMap<>();
//            error.put("error", "Error creating order: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    @PostMapping("/verify")
//    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> payload) {
//        try {
//            if (!payload.containsKey("razorpay_order_id") ||
//                !payload.containsKey("razorpay_payment_id") ||
//                !payload.containsKey("razorpay_signature")) {
//                Map<String, Object> error = new HashMap<>();
//                error.put("error", "Missing required payment fields");
//                return ResponseEntity.badRequest().body(error);
//            }
//
//            String orderId = payload.get("razorpay_order_id");
//            String paymentId = payload.get("razorpay_payment_id");
//            String signature = payload.get("razorpay_signature");
//
//            String body = orderId + "|" + paymentId;
//            String expectedSignature = generateHmacSHA256(body, razorpayKeySecret);
//
//            if (expectedSignature.equalsIgnoreCase(signature)) {
//                Map<String, Object> success = new HashMap<>();
//                success.put("message", "Payment verified successfully");
//                return ResponseEntity.ok(success);
//            } else {
//                Map<String, Object> error = new HashMap<>();
//                error.put("error", "Invalid payment signature");
//                return ResponseEntity.badRequest().body(error);
//            }
//        } catch (Exception e) {
//            Map<String, Object> error = new HashMap<>();
//            error.put("error", "Verification failed: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    private String generateHmacSHA256(String data, String secret) throws Exception {
//        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
//        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
//        sha256_HMAC.init(secretKey);
//        byte[] hash = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
//        StringBuilder hexString = new StringBuilder();
//        for (byte b : hash) {
//            String hex = Integer.toHexString(0xff & b);
//            if (hex.length() == 1) hexString.append('0');
//            hexString.append(hex);
//        }
//        return hexString.toString();
//    }
//}