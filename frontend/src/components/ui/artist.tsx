import { getPopularityMessage } from "@/lib/messages";
import Link from "next/link";

export const ArtistDescription = ({
  id,
  name,
  type,
  popularity,
  genres,
  total,
}) => {
  return (
    <div className="flex flex-col justify-around p-4 h-full">
      <Link href={`/artist/${id}`}>
        <h1 className="font-bold">{name}</h1>
      </Link>
      <h2 className="font-light">About {name}</h2>
      <p className="font-extralight">
        {name} is an {type} how has been {getPopularityMessage(popularity)} with
        about {total} famous singles/albums based on {genres.join(",")} genres.
      </p>
    </div>
  );
};
