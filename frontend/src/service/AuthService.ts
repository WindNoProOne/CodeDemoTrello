import axios from "axios";
import { User } from "../types/User";
import { BASE_URL } from "./Config";

type AuthProps = {
  item: User;
};

class AuthService {
  public static async login(authProps: AuthProps): Promise<User> {
    const { item } = authProps;
    const { email, password } = item;

    try {
      const response = await axios.post(`${BASE_URL}/auth/login`, {
        email,
        password,
      });
      const user: User = response.data;

      const token = user.token;
      if (token) {
        localStorage.setItem("authToken", token);
      }
      localStorage.setItem("email", email);
      console.log(user);
      return user;
    } catch (error) {
      if (axios.isAxiosError(error)) {
        throw new Error(error.response?.data?.message || "Login Failed");
      }
      throw new Error("Login Failed");
    }
  }
}

export default AuthService;
