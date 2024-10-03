import {
  TextInput,
  PasswordInput,
  Text,
  Paper,
  Group,
  Button,
  Divider,
  Anchor,
  Stack,
  Container,
  Notification,
} from "@mantine/core";
import { GoogleButton } from "./GoogleButton";
import { TwitterButton } from "./TwitterButton";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import AuthService from "../../service/AuthService";

export function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setError(null);

    try {
      const response = await AuthService.login({
        item: { id: "", name: "", email, password },
      });
      if (response && response.token) {
        localStorage.setItem("authToken", response.token);
        localStorage.setItem("role", response.role || "");

        navigate("/");
      }
    } catch (err) {
      setError("Login failed. Please check your credentials.");
    }
  };

  return (
    <Container mt="10%" size={530}>
      <Paper radius="md" p="xl">
        <Text size="lg" w={500}>
          Welcome to Trello, login with
        </Text>

        <Group grow mb="md" mt="md">
          <GoogleButton radius="xl">Google</GoogleButton>
          <TwitterButton radius="xl">Twitter</TwitterButton>
        </Group>

        <Divider
          label="Or continue with email"
          labelPosition="center"
          my="lg"
        />

        <form onSubmit={handleSubmit}>
          <Stack>
            <TextInput
              required
              label="Email"
              placeholder="Enter your email"
              radius="md"
              value={email}
              onChange={(event) => setEmail(event.currentTarget.value)}
            />

            <PasswordInput
              required
              label="Password"
              placeholder="Your password"
              radius="md"
              value={password}
              onChange={(event) => setPassword(event.currentTarget.value)}
            />
          </Stack>

          {error && (
            <Notification
              title="Error"
              color="red"
              mt="md"
              onClose={() => setError(null)}
            >
              {error}
            </Notification>
          )}

          <Group justify="space-between" mt="xl">
            <Link to="/register">
              <Anchor component="button" type="button" c="dimmed" size="xs">
                Don't have an account? Register
              </Anchor>
            </Link>
            <Button type="submit" radius="xl" fullWidth>
              Login
            </Button>
          </Group>
        </form>
      </Paper>
    </Container>
  );
}
