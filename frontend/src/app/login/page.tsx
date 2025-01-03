import { Button } from "@/components/ui/button";
import { IconBrandSpotify } from "@tabler/icons-react";
import Link from "next/link";

export default function Login() {
  return (
    <div className="w-full h-full flex justify-center items-center">
      <section
        id="login-container"
        className="w-96 h-[32rem] rounded-xl shadow-md flex flex-col text-center p-8"
      >
        <h1 className="font-semibold grow grid place-items-center">
          Login to Spotify App
        </h1>
        <div className="grow-[11] grid place-items-center">
          <Link href="http://localhost:3000/auth/spotify">
            <Button>
              <IconBrandSpotify className="fill-green-500 stroke-black !w-auto !h-auto" />
              Sign in with Spotify
            </Button>
          </Link>
        </div>
      </section>
    </div>
  );
}
