import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourtViewComponent } from './court-view.component';

describe('CourtViewComponent', () => {
  let component: CourtViewComponent;
  let fixture: ComponentFixture<CourtViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CourtViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CourtViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
