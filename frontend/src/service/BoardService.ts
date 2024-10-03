import axios from "axios";
import { TypeBoard } from "../types/Board";
import { BASE_URL } from "./Config";
import { useQuery, useMutation, useQueryClient } from "react-query";

export type BoardProps = {
  item: TypeBoard;
};

class BoardService {
  static async getAllBoard(token: string): Promise<TypeBoard[]> {
    try {
      const response = await axios.get(`${BASE_URL}/admin/board`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    } catch (error) {
      throw new Error("Get All View Data False");
    }
  }

  static useGetAllBoards(token: string) {
    return useQuery<TypeBoard[], Error>(
      ["tasks", token],
      () => this.getAllBoard(token),
      {
        enabled: !!token,
        onError: (error) => {
          console.error("Error fetching tasks:", error);
        },
      }
    );
  }

  static async createBoard(
    token: string,
    boardProps: BoardProps
  ): Promise<TypeBoard> {
    const { item } = boardProps;
    try {
      const response = await axios.post(`${BASE_URL}/admin/boards`, item, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });
      return response.data as TypeBoard;
    } catch (error) {
      console.error("Error creating board:", error);
      throw new Error("Failed to create board");
    }
  }

  static useCreateBoard = () => {
    const queryClient = useQueryClient();

    return useMutation(
      ({ token, boardProps }: { token: string; boardProps: BoardProps }) =>
        BoardService.createBoard(token, boardProps),
      {
        onSuccess: () => {
          queryClient.invalidateQueries("getAllBoard");
        },
        onError: (error) => {
          console.error("Error creating board:", error);
        },
      }
    );
  };
}

export default BoardService;
