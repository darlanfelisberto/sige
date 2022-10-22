import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplateLibComponent } from './template-lib.component';

describe('TemplateLibComponent', () => {
  let component: TemplateLibComponent;
  let fixture: ComponentFixture<TemplateLibComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TemplateLibComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TemplateLibComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
