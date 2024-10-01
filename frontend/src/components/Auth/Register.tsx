import { useForm } from "@mantine/form";
import {
  TextInput,
  PasswordInput,
  Text,
  Paper,
  Group,
  Button,
  Divider,
  Checkbox,
  Anchor,
  Stack,
  Container,
  PaperProps,
} from "@mantine/core";
import { GoogleButton } from "./GoogleButton";
import { TwitterButton } from "./TwitterButton";

export function Register(props: PaperProps) {
  const form = useForm({
    initialValues: {
      email: "",
      name: "",
      password: "",
      terms: false,
    },

    validate: {
      email: (val) => (/^\S+@\S+$/.test(val) ? null : "Invalid email"),
      name: (val) =>
        val.length > 1 ? null : "Name must be at least 2 characters",
      password: (val) =>
        val.length >= 6 ? null : "Password must include at least 6 characters",
      terms: (val) =>
        val ? null : "You must agree to the terms and conditions",
    },
  });

  return (
    <Container mt="10%" size={530}>
      <Paper radius="md" p="xl" withBorder {...props}>
        <Text size="lg" w={500}>
          Create your account
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
              label="Name"
              placeholder="Your name"
              value={form.values.name}
              onChange={(event) =>
                form.setFieldValue("name", event.currentTarget.value)
              }
              error={form.errors.name}
              radius="md"
            />

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

            <Checkbox
              label="I agree to the terms and conditions"
              checked={form.values.terms}
              onChange={(event) =>
                form.setFieldValue("terms", event.currentTarget.checked)
              }
              error={form.errors.terms}
            />
          </Stack>

          <Group justify="space-between" mt="xl">
            <Anchor component="button" type="button" color="dimmed" size="xs">
              Already have an account? Login
            </Anchor>
            <Button type="submit" radius="xl" fullWidth>
              Register
            </Button>
          </Group>
        </form>
      </Paper>
    </Container>
  );
}
