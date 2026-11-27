import { TestBed } from '@angular/core/testing';
import { Admissions } from './admissions';

describe('Admissions', () => {
  let service: Admissions;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Admissions);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
