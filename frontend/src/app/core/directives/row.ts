import { Directive, inject, input, TemplateRef } from '@angular/core';

interface RowContext<T> {
  $implicit: T;
  index: number;
}

@Directive({
  selector: 'ng-template[sioRow]',
})
export class Row<T> {
  readonly templateRef = inject(TemplateRef);
  readonly sioRow = input.required<readonly T[]>();

  static ngTemplateContextGuard<T>(dir: Row<T>, ctx: unknown): ctx is RowContext<T> {
    return true;
  }
}
