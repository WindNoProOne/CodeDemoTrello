import { Paper, Text } from "@mantine/core";
import { TypeBoard } from "../../../types/Board";
import { PiDotsThreeBold } from "react-icons/pi";

interface Props {
  board: TypeBoard;
}

function Task(props: Props) {
  const { board } = props;
  return (
    <div>
      <Paper w={300} p="md" shadow="xl" radius="8px">
        <div style={{ display: "flex", justifyContent: "space-between" }}>
          <Text fw={500} style={{ fontSize: "14px" }}>
            {board.name}
          </Text>
          <PiDotsThreeBold />
        </div>
        <div>Body</div>
        <div>Footer</div>
      </Paper>
    </div>
  );
}

export default Task;
