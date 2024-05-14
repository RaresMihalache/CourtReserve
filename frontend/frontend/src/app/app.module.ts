import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CourtManagementComponent } from './pages/court-management/court-management.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from 'src/material.module';
import { RegisterPageComponent } from './pages/register-page/register-page.component';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ResetPasswordComponent } from './pages/reset-password/reset-password.component';

import { CourtViewComponent } from './pages/court-view/court-view.component';
import { PartnerRequestComponent } from './pages/partner-request/partner-request.component';
import { SubscriptionViewComponent } from './pages/subscription-view/subscription-view.component';
import { ViewReservationsComponent } from './pages/view-reservations/view-reservations.component';
import { TakeoverRequestsComponent } from './pages/takeover-requests/takeover-requests.component';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterPageComponent,
    CourtManagementComponent,
    LoginComponent,
    DashboardComponent,
    ResetPasswordComponent,
    CourtViewComponent,
    PartnerRequestComponent,
    SubscriptionViewComponent,
    ViewReservationsComponent,
    TakeoverRequestsComponent,
    LandingPageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
