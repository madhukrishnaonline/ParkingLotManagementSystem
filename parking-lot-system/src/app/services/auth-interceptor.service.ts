import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AuthService } from "./auth-service";

@Injectable({
    providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor {
    constructor(private service: AuthService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token = this.service.isTokenAvailable();
        if (token) {
            const clonedReq = req.clone({
                setHeaders: {
                    Authorization: token
                }
            });
            return next.handle(clonedReq);
        }
        return next.handle(req);
    }

}