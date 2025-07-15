import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { LoginRequest } from "../models/login.request";
import { ManagerModelContract } from "../models/manager-model";

declare const baseURL: string;
@Injectable({
    providedIn: 'root'
})
export class ManagerService {
    constructor(private http: HttpClient) { }

    // Register User
    public registerUser(form: ManagerModelContract): Observable<string> {
        return this.http.post<string>(`${baseURL}register`, form);
    }

    //login
    login(loginRequest: LoginRequest): Observable<any> {
        return this.http.post<any>(`${baseURL}login`, loginRequest, { observe: 'response' }).pipe(
            map(response => {
                const token = response.headers.get('Authorization');
                if (token) {
                    sessionStorage.setItem("Auth_Token", token);
                }
                return response;
            })
        );
    }

    getUserById(id: string | null): Observable<ManagerModelContract> {
        return this.http.get<ManagerModelContract>(`${baseURL}id/${id}`);
    }

    getAllManagers(): Observable<ManagerModelContract[]> {
        return this.http.get<ManagerModelContract[]>(`${baseURL}getAllManagers`);
    }
}

