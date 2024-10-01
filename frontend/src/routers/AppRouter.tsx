import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Home from "../components/Home/Home";
import { Login } from "../components/Auth/Login";
import { Register } from "../components/Auth/Register";

const router = createBrowserRouter([
  {
    index: true,
    path: "/",
    element: <Home />,
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/register",
    element: <Register />,
  },
]);

function AppRouter() {
  return (
    <div>
      <RouterProvider router={router} />
    </div>
  );
}

export default AppRouter;
