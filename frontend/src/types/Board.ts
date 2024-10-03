import { User } from "./User";
export type Id = string | number;

export type TypeBoard = {
  id?: Id;
  name: string;
  position?: number;
  createUser?: User;
};
