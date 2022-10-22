import { TestBed } from '@angular/core/testing';

import { TemplateLibService } from './template-lib.service';

describe('TemplateLibService', () => {
  let service: TemplateLibService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TemplateLibService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
