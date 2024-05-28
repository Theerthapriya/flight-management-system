import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightsSchedulesComponent } from './flights-schedules.component';

describe('FlightsSchedulesComponent', () => {
  let component: FlightsSchedulesComponent;
  let fixture: ComponentFixture<FlightsSchedulesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FlightsSchedulesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FlightsSchedulesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
