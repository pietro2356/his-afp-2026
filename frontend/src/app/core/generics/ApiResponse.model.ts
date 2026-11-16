export interface ApiResponse<T> {
  status: string;
  data: T;
}

export interface Page<T> {
  content: T
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  size: number;
  totalElements: number;
  totalPages: number;
}
