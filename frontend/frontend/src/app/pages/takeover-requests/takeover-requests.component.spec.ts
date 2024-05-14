import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TakeoverRequestsComponent } from './takeover-requests.component';

describe('TakeoverRequestsComponent', () => {
  let component: TakeoverRequestsComponent;
  let fixture: ComponentFixture<TakeoverRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TakeoverRequestsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TakeoverRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
