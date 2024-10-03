import { Button, Input, Paper, Text } from "@mantine/core";
import { IoAdd } from "react-icons/io5";
import BoardService from "../../../service/BoardService";
import Task from "./Task";
import { useState } from "react";
import { MdOutlineCancelPresentation } from "react-icons/md";

const Board = () => {
  const [newBoardName, setNewBoardName] = useState<string>("");
  const [showForm, setShowForm] = useState<boolean>(false);
  const token = localStorage.getItem("authToken") || "";
  const { data: boards } = BoardService.useGetAllBoards(token);
  const createBoard = BoardService.useCreateBoard();

  const handleAddNewBoard = () => {
    setShowForm(true);
  };

  const handleAddList = () => {
    if (!newBoardName.trim()) {
      console.log("Please enter a name for the new board");
      return;
    }

    createBoard.mutate({
      token,
      boardProps: { item: { name: newBoardName } },
    });

    setNewBoardName("");
    setShowForm(false);
  };

  return (
    <div style={{ marginLeft: "25px", display: "flex", gap: "50px" }}>
      <div style={{ display: "flex", alignItems: "center", gap: "60px" }}>
        {boards?.map((board) => (
          <div key={board.id}>
            <Text size="16px" fw={500}>
              <Task board={board} />
            </Text>
          </div>
        ))}
      </div>

      {!showForm && (
        <div>
          <Button
            w={272}
            p={12}
            bg="#ffffff3d"
            radius={12}
            size="md"
            ml={20}
            leftSection={<IoAdd size={20} />}
            onClick={handleAddNewBoard}
          >
            <Text size="14px" fw={500}>
              Add New Board
            </Text>
          </Button>
        </div>
      )}

      {showForm && (
        <div>
          <Paper w={300} p="md" shadow="xl" radius="8px">
            <Input
              placeholder="Enter List Name"
              value={newBoardName}
              onChange={(e) => setNewBoardName(e.target.value)}
              style={{
                border: "2px solid #2952ea",
                borderRadius: "4px",
              }}
            />
            <div
              style={{
                marginTop: "10px",
                display: "flex",
                gap: "23px",
                alignItems: "center",
              }}
            >
              <Button size="xs" onClick={handleAddList}>
                Add List
              </Button>
              <MdOutlineCancelPresentation
                size={25}
                onClick={() => setShowForm(false)}
              />
            </div>
          </Paper>
        </div>
      )}
    </div>
  );
};

export default Board;
