import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterPageComponent } from './pages/register-page/register-page.component';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ResetPasswordComponent } from './pages/reset-password/reset-password.component';
import { CourtManagementComponent } from './pages/court-management/court-management.component';
import { CourtViewComponent } from './pages/court-view/court-view.component';
import { PartnerRequestComponent } from './pages/partner-request/partner-request.component';
import { SubscriptionViewComponent } from './pages/subscription-view/subscription-view.component';
import { ViewReservationsComponent } from './pages/view-reservations/view-reservations.component';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';
import { TakeoverRequestsComponent } from './pages/takeover-requests/takeover-requests.component';

const routes: Routes = [
  {path: '', component: LandingPageComponent},
  {path: 'court-view', component: CourtViewComponent},
  {path: 'view-reservations', component: ViewReservationsComponent},
  {path:'register', component: RegisterPageComponent},
  {path:'login', component: LoginComponent},
  {path:'dashboard', component: DashboardComponent},
  {path:'resetPassword', component: ResetPasswordComponent},
  {path:'court-management', component: CourtManagementComponent},
  {path:'partner-request', component: PartnerRequestComponent},
  {path:'subscriptions', component: SubscriptionViewComponent},
  {path: 'takeover-requests', component: TakeoverRequestsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
