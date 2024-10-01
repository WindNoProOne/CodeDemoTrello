import { useForm } from "@mantine/form";
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
  PaperProps,
} from "@mantine/core";
import { GoogleButton } from "./GoogleButton";
import { TwitterButton } from "./TwitterButton";
import { Link } from "react-router-dom";

export function Login(props: PaperProps) {
  const form = useForm({
    initialValues: {
      email: "",
      password: "",
      terms: true,
    },

    validate: {
      email: (val) => (/^\S+@\S+$/.test(val) ? null : "Invalid email"),
      password: (val) =>
        val.length <= 6 ? "Password must include at least 6 characters" : null,
    },
  });

  return (
    <Container mt="10%" size={530}>
      <Paper radius="md" p="xl" withBorder {...props}>
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

        <form onSubmit={form.onSubmit(() => {})}>
          <Stack>
            <TextInput
              required
              label="Email"
              placeholder="Enter your email"
              value={form.values.email}
              onChange={(event) =>
                form.setFieldValue("email", event.currentTarget.value)
              }
              error={form.errors.email}
              radius="md"
            />

            <PasswordInput
              required
              label="Password"
              placeholder="Your password"
              value={form.values.password}
              onChange={(event) =>
                form.setFieldValue("password", event.currentTarget.value)
              }
              error={form.errors.password}
              radius="md"
            />
          </Stack>

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
