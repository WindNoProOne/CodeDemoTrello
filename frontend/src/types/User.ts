export type Id = string | number;

export type User = {
  id: Id;
  name: string;
  email: string;
  password: string;
  token?: string;
  role?: string;
};
