export const getPopularityMessage = (count: number) => {
  let message;
  if (count < 75) {
    message = "create popular songs";
  } else if (count < 50) {
    message = "popular on their country";
  } else if (count < 25) {
    message = "listining by locals";
  } else {
    message = "listened all over the world";
  }
  return message;
};
