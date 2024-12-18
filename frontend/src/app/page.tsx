import { Button } from "@/components/ui/button";
import { IconBrandSpotify } from '@tabler/icons-react'

export default function Home() {
  return (
    <div className="w-full h-full flex justify-center items-center">
      <section id="login-container" className="w-96 h-[32rem] rounded-xl shadow-md flex flex-col text-center p-8">
          <h1 className="font-semibold grow grid place-items-center">Login to Spotify App</h1>
          <div className="grow-[11] grid place-items-center">
          <Button>
            <IconBrandSpotify className="fill-green-500 stroke-black !w-auto !h-auto"/>
            Sign in with Spotify
          </Button>
          </div>
      </section>
    </div>
  );
}
