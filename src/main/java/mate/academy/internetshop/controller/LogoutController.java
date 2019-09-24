package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.annotation.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.service.BucketService;

public class LogoutController extends HttpServlet {

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("Mate")) {
                cookie.setValue(null);
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
        }
        Long userId = (Long) req.getSession(false).getAttribute("userId");
        req.getSession(false).removeAttribute("userId");
        Bucket bucket = bucketService.getBucket(userId);
        bucketService.delete(bucket.getId());
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
